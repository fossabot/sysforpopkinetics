#!/usr/bin/perl -w
########################################################################
#
# This file is part of the System for Population Kinetics (SPK), which
# was developed with support from NIH grants RR-12609 and P41-
# EB001975. Please cite these grants in any publication for which this
# software is used and send a notification to the address given above.
#
# SPK is Copyright (C) 1998-2003, by the University of Washington,
# Resource Facility for Population Kinetics, and is made available as
# free open source software under the terms of the University of
# Washington Free-Fork License as a public service.  A copy of the
# License can be found in the COPYING file in the root directory of this
# distribution or can be obtained from
#     Resource Facility for Population Kinetics
#     Department of Bioengineering Box 352255
#     University of Washington
#     Seattle, WA 98195-2255
########################################################################

=head1 NAME

spkrund.pl -- the SPK Run Time Daemon

=head1 SYNOPSIS

spkrund.pl database host dbuser dbpasswd

=head1 ABSTRACT

The SPK run daemon executes continuously on the CSPK server. It
selects jobs from the run queue, forks processes to compile, link
and run them and inserts results into the database.

=head1 DESCRIPTION

The program expects the following arguments:

    $database
        The name of the SPK database
    $host
        The host on which the SPK database resides
    $dbuser
        A database username which has read/write access to the 
        job table
    $dbpasswd
        The password associated with the username

The first think that spkcmp.pl does after starting up is to call
Proc::Daemon::Init to make it into a daemon, by shedding its 
inheirited environment and becoming a direct child of the system
init process.

It then opens the system log so that it has a place to record progress
and error messages.

It attempts to create a lock-file if one does not already exist. The
method that is used assures that the process of query and creation
is atomic.  If the lock-file already exists, the program writes an
error message to the system log and terminates, because only one 
copy of spkrund.pl can be allowed run at any given time.

Next, it opens the database.

The program designates itself to be a process group leader. This
way it will be able to send signals to all of its descendents without
having to know their PIDs. 

The "stop" subroutine is designated to catch the TERM signal,
when it is received. As explained below, this will allow for
an orderly shutdown of the daemon and its sub-processes.

The last major step in the start-up sequence is to select from the
database all jobs with a state_code field set to 'run'.  These
jobs, if any exist, had been in the process of running when 
the daemon last shut down.  All such jobs are rerun.

At this point, the program enters an endless loop from which it will
escape only upon receipt of a signal. It queries the
database to discover whether or not a job has been added to the run
queue.  If so, a copy of runner is started as an independent
sub-process, working in its own directory on input provided by the
job. The daemon then checks to see if any child processes have 
terminated. If so, it moves them to "end" status and stores the
results in the database. The daemon sleeps a second, before continuing
its loop.

The normal way in which spkcmp.pl is terminated is by using the
Unix "kill" command to send it the TERM signal.  When TERM is received,
execution is passed to the 'stop' subroutine, which had previously
been designated to catch this signal.

To avoid a loop, 'stop' sets the signal mask to ignore any
subsequent TERM signals. It then sends the TERM signal to every process
in its process group, which consists of the daemon itself and all of its
descendents. It waits for all sub-processes (which are 
instances of the runner) to terminate.  It closes the database
and the system log, then dies.

=head1 RETURNS

Nothing, because it has no parent (other than init) to which an exit
code might be returned.  The program does, however, write event messages
to the system log.

=cut

use strict;
use bytes;

use Fcntl qw(:DEFAULT :flock);
use File::Path;
use POSIX qw(:signal_h);
use Proc::Daemon;
use Spkdb('connect', 'disconnect', 'de_q2r', 'en_q2r', 'get_run_jobs', 'end_job');
use Sys::Syslog('openlog', 'syslog', 'closelog');

my $database = shift;
my $host     = shift;
my $dbuser   = shift;
my $dbpasswd = shift;

my $dbh;
my $build_failure_exit_value = 101;
my $database_open = 0;
my $filename_makefile = "generatedMakefile";
my $filename_data = "data.xml";
my $filename_serr = "software_error";
my $filename_results = "results.xml";
my $filename_source = "source.xml";
my $filename_runner = "driver";
my $service_name = "spkrund";
my $lockfile_path = "/tmp/lock_$service_name";
my $lockfile_exists = 0;
my $pathname_make = "/usr/bin/make";
my $pathname_tar = "/bin/tar";
my $prefix_working_dir = "spkrun-";
my $tmp_dir = "/tmp";

sub death {
    #
    # Only call this from the parent, never from the child
    #
    my $level = shift;
    my $msg = shift;

    # log the reason for termination
    syslog($level, $msg);

    # close the connection to the database
    if ($database_open) {
	&disconnect($dbh)
    }

    # remove the lockfile
    if ($lockfile_exists) {
	unlink($lockfile_path);
    }
    # log final message, then close the system log
    syslog("info", "stop");
    closelog();

    die;
}
sub fork_runner {
    use Errno qw(EAGAIN);

    my $jrow = shift;
    my $job_id = $jrow->{'job_id'};
    my $cpp_source = $jrow->{'cpp_source'};
    my $pid;

    # Block SIGCHLD while we are in this subroutine
    my $sigset   = POSIX::SigSet->new;
    my $blockset = POSIX::SigSet->new(SIGCHLD);
    sigprocmask(SIG_BLOCK, $blockset, $sigset)
	or death('emerg', "could not block SIGCHLD in repear");

    # NOTE: Working Directory Name
    #
    # The name of the run-time working directory can change over the
    # life of the job:
    # 1. A working directory must be created before the run-time is
    #    forked as a child of this daemon, so that the run-time
    #    will start up in a directory unique to itself in which it
    #    will find its input files and into which it can write
    #    its output.  So that the name is unique, the unique job_id
    #    is suffixed to a prefix indicating that this is a compile.
    # 2. After the fork, the pid of the child is known.  At that 
    #    point, the name of the directory is changed so that the
    #    suffix is the job_id.  This will be needed later when the
    #    child dies, because at that point the parent (this daemon)
    #    will be provided with the pid but will not know the job_id.
    #    After going to the working directory, the parent will
    #    discover the job_id by reading the contents of the "job_id"
    #    file, written in step 1.
    # 3. If a core dump was created when the child died, the 
    #    name of the working directory is changed so that, once
    #    again, the suffix is the job_id. This makes it easy for
    #    software engineers to find the dump in order to analyze
    #    it.  All other working directories are removed, when 
    #    the compiler terminates.

    # Create a working directory
    my $unique_name = "$prefix_working_dir$job_id";
    my $working_dir = "$tmp_dir/$unique_name";
    mkdir($working_dir, 0700) 
	or death("emerg", "couldn't create working directory: $working_dir");

    # Expand the cpp_source archive in the working directory
    chdir $working_dir
	or death('emerg', "couldn't change directory to $working_dir");
    my $archive_name = "cpp_source.tar";
    open(FH, ">$archive_name")
	or death('emerg', "could not open the $archive_name file in $working_dir");
    print FH $cpp_source;
    close(FH);
    if (-s $archive_name != length $cpp_source) {
	death('emerg', "write of $archive_name failed");
    }
    my @args = ($pathname_tar, "xf", $archive_name);
    unless (system(@args) == 0)  {
	death("emerg", "couldn't expand $archive_name");
    }
    File::Path::rmtree($archive_name, 0, 0);


    # Fork the process into parent and child

  FORK: {
      if ($pid = fork) {
	  # This is the parent
	  syslog("info", "forked process with pid=$pid for job_id=$job_id");
      }
      elsif (defined $pid) {
	  # This is the child.  NOTE: do not call death() in this block.
	  # Open the system log for the child, so that messages are properly
	  # identified as coming from the child.
	  closelog();
	  openlog("$service_name-child, pid=$$", 'cons', 'daemon');

	  # Change the name of the working directory to reflect the child pid.
	  # When the child terminates, the parent will be provided with this pid,
	  # and hence will be able to identify the working directory of the
	  # child.
	  rename $working_dir, "$tmp_dir/$prefix_working_dir$$"
	      or do {
		  syslog('emerg', "can't rename working directory");
		  die;
	      };
	  # Compile and link the runner
	  @args = ($pathname_make, "-f", $filename_makefile);
	  unless (system(@args) == 0)  {
	      $! = 101;
	      #$? = $build_failure_exit_value << 8;
	      die;
	  }
	  # Redirect Standard Error to a file
	  open STDERR, ">$filename_serr";

	  # execute the spkrunner
	  @args = ("./$filename_runner", $filename_source, $filename_data);
	  my $e = exec(@args);

	  # this statement will never be reached, unless the exec failed
	  if (!$e) {
	      syslog("emerg", "couldn't exec $filename_runner for $job_id");
	      die;
	  }
      }
      elsif ($! == EAGAIN) {
	  # EAGAIN indicates a fork error which may be recoverable
	  sleep(5);
	  redo FORK;
      }
      else {
	  death("emerg", "fork of run-time driver failed");
      }
  }
    sigprocmask(SIG_SETMASK, $sigset)
	or death('emerg', "could not restore SIGCHLD in reaper");
}
sub format_error_report {
    my $content = shift;
    my $report = "<spkreportML>\n";
    $report .= "  <error_message>\n";
    $report .= "    $content\n";
    $report .= "  </error_message>\n";
    $report .= "</spkreportML>\n";
}
sub reaper {
    # Handler for SIGCHLD which processes any children which have terminated
    # Block SIGCHLD while we are in here 
    my $sigset   = POSIX::SigSet->new;
    my $blockset = POSIX::SigSet->new(SIGCHLD);
    sigprocmask(SIG_BLOCK, $blockset, $sigset)
	or death('emerg', "could not block SIGCHLD in repear");

    while ((my $child_pid = waitpid(-1, &WNOHANG)) > 0) {
	my $child_exit_value    = $? >> 8;
	my $child_signal_number = $? & 0x7f;
	my $child_dumped_core   = $? & 0x80;
	my $job_id;
	my $report;
	my $status_msg = "";

	# Get the job_id from the file spkcmpd.pl wrote to the working
	# directory of this process
	my $working_dir = "$prefix_working_dir$child_pid";
	chdir "$tmp_dir/$working_dir";
	open(FH, "job_id")
	    or death('emerg', "can't open $tmp_dir/$working_dir/job_id");
	read(FH, $job_id, -s FH);
	close(FH);

	# Normal termination at end of run
	if (-f $filename_results) {

	    # Read the results file
	    my $results;
	    open(FH, $filename_results)
		or death('emerg', "can't open $tmp_dir/$working_dir/$filename_results");
	    read(FH, $results, -s FH);
	    close(FH);

	    # Copy the results to the database and a message to the system log
	    &end_job($dbh, $job_id, "srun", $report)
		or death('emerg', "job_id=$job_id: $Spkdb::errstr");
	    syslog('info', "job_id=$job_id terminated normally");

	    # Remove the working directory
	    File::Path::rmtree("$tmp_dir/$working_dir");
	}
	# Error termination
	else {
	    my $end_code = "serr";
	    my $err_msg = "";
	    my $err_rpt = "";
	    # Prepare part of an error message
	    if ($child_exit_value != 0) {
		$status_msg .= "exit value = $child_exit_value; ";
	    }
	    if ($child_signal_number == SIGSEGV) {
		$status_msg .= "segmentation fault; ";
	    }
	    elsif($child_signal_number != 0) {
		$status_msg .= "received signal number $child_signal_number";
	    }
	    if ($child_dumped_core) {
		$status_msg .= "core dump in $tmp_dir/$prefix_working_dir$job_id";
	    }

	    # Did the run die due to a software error that was caught?
	    if (-f $filename_serr && -s $filename_serr > 0) {
		open(FH, $filename_serr)
		    or death('emerg', "can't open $tmp_dir/$working_dir/$filename_serr");
		read(FH, $err_rpt, -s FH);
		close(FH);
		$err_msg = "run died due to SPK bug";
	    }
	    # Was run aborted by an operator (suspecting a bug)
	    elsif ($child_signal_number == SIGABRT
		   || $child_signal_number == SIGTERM) {
		$err_msg = "run killed by operator: $status_msg";
	    }
	    # Did the run fail when we tried to build the driver with "make"?
	    elsif ($child_exit_value == $build_failure_exit_value) {
		$err_msg = "c++ program generated by SPK failed to build";
	    }
	    # Died unexpectedly (probably a hard fault)
	    else {
		$end_code = "herr";
		$err_msg = "run died unexpectedly: $status_msg";
	    }
	    # Place error report in database messages in system log
	    $report = format_error_report("$err_msg: $err_rpt");
	    &end_job($dbh, $job_id, $end_code, $report)
		or death('emerg', "job_id=$job_id: $Spkdb::errstr");
	    syslog('info', "job_id=$job_id: $err_msg");

	    # Rename working directory to make evidence easier to find
	    rename "$tmp_dir/$working_dir", "$tmp_dir/$prefix_working_dir$job_id"
		or death('emerg', "couldn't rename working directory");
	}
    }
    sigprocmask(SIG_SETMASK, $sigset)
	or death('emerg', "could not restore SIGCHLD in reaper");
}
sub start {
    # open the system log and record that we have started
    openlog("$service_name, pid=$$", 'cons', 'daemon');
    syslog('info', 'start');

    # create a lockfile and store our pid, to be used later by stop()
    sysopen(FH, $lockfile_path, O_RDWR | O_CREAT)
	or death("emerg", "can't start -- could not create lockfile");
    my @info = stat(FH);
    $info[7] == 0 
	or death("emerg", "can't start -- $lockfile_path already exists");
    print FH $$;
    close(FH);
    $lockfile_exists = 1;

    # open a connection to the database
    $dbh = &connect($database, $host, $dbuser, $dbpasswd)
	or death("emerg", "can't connect to database=$database, host=$host");
    syslog("info", "connected to database=$database, host=$host");
    $database_open = 1;
}
sub stop {
    # We have received the TERM signal. 
    
    # Become insensitive to TERM signal we are about to broadcast
    local $SIG{'TERM'} = 'IGNORE';

    # Remove reaper as catcher of SIGCHLD
    $SIG{'CHLD'} = 'DEFAULT';

    # send the TERM signal to every member of our process group
    kill('TERM', -$$);

    # wait for all of our children to die
    my $child_pid;
    while (($child_pid = wait()) != -1) {
	syslog('info', "process $child_pid received the TERM signal");
    }
    # now we can die
    death('info', 'received the TERM signal (normal mode of termination)');
}
my $row;
my $row_array;

# become a daemon
Proc::Daemon::Init();

# initialize
start();

# Add directories of shared libraries to the load path
$ENV{LD_LIBRARY_PATH} = "/usr/lib:/usr/local/lib";

# Create a new process group, with this process as leader.  This will
# allow us to send the TERM signal to all of our children with a single
# command.
setpgrp(0, 0);

# Block certain signals
$SIG{'ABRT'} = 'IGNORE';
$SIG{'HUP'}  = 'IGNORE';
$SIG{'INT'}  = 'IGNORE';
$SIG{'QUIT'} = 'IGNORE';

# Designate handlers for certain signals
$SIG{'CHLD'} = \&reaper;
$SIG{'TERM'} = \&stop;

# rerun any runs that were interrupted when we last terminated
$row_array = &get_run_jobs($dbh);
syslog('info', "looking for interrupted computational runs");
if (defined $row_array) {
    foreach $row (@$row_array) {
	&fork_runner($row);
    }
}
else {
    death("emerg", "error reading database: $Spkdb::errstr");
}

# loop until interrupted by a signal
use POSIX ":sys_wait_h";
my $pid_of_deceased_child;
my @args;
my $job_id;

while(1) {
    # if there is a job queued-to-run, fork the runner
    $row = &de_q2r($dbh);
    if (defined $row) {
	if ($row) {
	    &fork_runner($row);
	}
    }
    else {
	death("emerg", "error reading database: $Spkdb::errstr");
    }
    # sleep for a second
    sleep(1); # DO NOT REMOVE THIS LINE
              # or else this daemon will burn all your CPU cycles!
};