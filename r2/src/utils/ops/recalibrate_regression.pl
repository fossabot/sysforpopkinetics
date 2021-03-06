#!/usr/bin/perl -w

use lib "/usr/local/bin";
use strict;
use English;
use File::Path;
use XML::Simple;
use Data::Dumper;
use Getopt::Long qw(:config no_auto_abbrev);
use Pod::Usage;

my $run_dir = "/usr/local/spk/share/working/spktest";
my $base_dir = "/usr/local/spk/ops/regression_test";
my %file_to_compare = ( 'cerr' => 'compilation_error.xml',
		        'srun' => 'result.xml');

my $config_file = "$base_dir/config/regression_test.xml";

my %opt = ();
GetOptions (\%opt, 'help', 'man', 'dump-config', 'config-file=s') 
    or pod2usage(-verbose => 0);
pod2usage(-verbose => 1)  if (defined $opt{'help'});
pod2usage(-verbose => 2)  if (defined $opt{'man'});

$EFFECTIVE_USER_ID == 0 
    or die "You must be root to run this program\n";

$ENV{'LOGNAME'} eq 'root' 
    or die "You must execute $ENV{'HOME'}/.bash_profile before running this program.\n"
    .  "If you become root with the command 'su -', that will happen automatically.\n";
 
$config_file = $opt{'config-file'} if (defined $opt{'config-file'});

my $config = XMLin($config_file, ForceArray => 1);

if (defined $opt{'dump-config'}) {
    print Dumper($config);
    exit 0;
}
my $job =  $config->{'cerr'}[0]{'job'};
my @alljobs = @$job;
$job =  $config->{'srun'}[0]{'job'};
push @alljobs, @$job;

my @args;

my ($sec, $min, $hour, $mday, $mon, $year) = localtime;
my $date = sprintf "%04d-%02d-%02d-%02d%02d-%02d", $year+1900, $mon+1, $mday, $hour, $min, $sec;


my $dir =  "$base_dir/retired/cerr/$date";
File::Path::mkpath $dir
    or die "could not make directory $dir\n";

@args = ("ssh", "cspkserver", "mkdir", "-p", "$base_dir/retired/srun/$date");
system(@args) == 0
    or die "could not make directory cspkserver:$base_dir/retired/$date";

for ('cerr', 'srun') {
     $job =  $config->{$_}[0]{'job'};
    /cerr/ and do {
	print "\nrecalibrating jobs with end_code 'cerr'";
	for my $job_id (@$job) {
	    print "\n\tjob $job_id";
	    @args = ("mv", "$base_dir/$_/spkcmptest-job-$job_id", 
		     "$base_dir/retired/$_/$date/spkcmptest-job-$job_id");
	    system(@args) == 0
		or die "couldn't retire spkcmptest-job-$job_id\n";
	    @args = ("cp", "-rf", "$run_dir/spkjob-$job_id", "$base_dir/$_/spkcmptest-job-$job_id");
	    system(@args) == 0
		or die "couldn't copy spkcmptest-job-$job_id\n";
	    print "\t\t\t\t\t\t\t\tOK";
	}
    };
    /srun/ and do {
	print "\nrecalibrating jobs with end_code 'srun'";
	for my $job_id (@$job) {
	    print "\n\tjob $job_id";
	    @args = ("ssh", "cspkserver", "mv", "$base_dir/$_/spkruntest-job-$job_id", 
		     "$base_dir/retired/$_/$date/spkruntest-job-$job_id");
	    system(@args) == 0
		or die "couldn't retire spkruntest-job$job_id\n";
	    @args = ("ssh", "cspkserver", "cp", "-rf", "$run_dir/spkjob-$job_id",
		     "$base_dir/$_/spkruntest-job-$job_id");
	    system(@args) == 0
		or die "couldn't copy spkruntest-job-$job_id\n";
	    print "\t\t\t\t\t\t\t\tOK";
	}
    };
}
print "\nRecalibration complete\n";

exit 0;

__END__


=head1 NAME

recalibrate_regression.pl -- recalibrate the regression testing environment

=head1 SYNOPSIS

recalibrate_regression.pl [--help] [--man] [--dump-config] [--config-file=file]

=head1 ABSTRACT

B<recalibrate_regression.pl> is an adjunct to the
 B<regression_test.pl> operations utility. The latter tests the current
deployment candidate against a set of jobs with known outcomes to
insure that the candidate does not "break" any features that worked
previously. Sometimes the regression test fails not because changes
that have been made to the software actually changed its essential
behavior, but because of formatting differences between the output
created by the candidate and the output created by its predecessor.
When this is determined to be the case, the test can be recalibrated
by replacing the expected output from one or more of the jobs by the
output produced by the same jobs when run by the candidate.
B<recalibrate_regression.pl> automates this replacement.


=head1 RUNNING

To run B<recalibrate_regression.pl>, you must satisfy these conditions:

=over 2

=item

Have exclusive use of the test environment.  This will have to be
coordinated with the other developers and testers.

=item

There has been no additional system testing of the jobs listed in the
configuration file since the last run of B<regression_test.pl>. 

=item

Have a terminal window or ssh window on whitechuck

=item

Log in as root, so that root's B<.bash_profile> is executed, which
runs B<keychain> and starts B<ssh_agent>.  One way to achieve this is
to log in as an ordinary user, then execute B<su -> to upgrade.
(Note that the B<-> sign is necessary. Without it, root's 
B<.bash_profile> will not be executed.)

=back

Note, that these are also the conditions for running B<regression_test.pl>,
hence they will normally already be in effect.

=head1 DESCRIPTION

B<recalibrate_regression.pl> can use the same configuration file that
is required for B<regression_test.pl>.  When running the regression
test, a default file is normally used, although another file can be
specified with one of the command-line arguments.  If the expected
output for all of the jobs specified in the configuration file is to
be replaced by the output of the candidate, the default file is the
right file to use.  If, however, only a subset of the jobs are to have
their output recalibrated, a configuration file which just specifies
this subset should be used.

It is assumed that working directories of all jobs to be recalibrated
are in B<aspkserver:/usr/local/spk/share/working/spktest> or in B<cspkserver:/usr/local/spk/share/working/spktest>, depending on whether the
job has an end_code of cerr or srun.  This is normally the case, when
B<recalibrate_regression.pl> is run after B<regression_test.pl>.

=head1 OPTIONS AND ARGUMENTS

=over 8

=item B<--help>

Print a brief help message and exit.

=item B<--man>

Print the manual page and exit.

=item B<--dump-config>

Dump out the data structure parsed from the xml configuration file 
and exit.

=item B<--config-file=file>

Specify the name of the configuration file.  If this argument is not
present, the configuration file name is assumed to be
"regression_test.xml".  See the CONFIGURATION section for the format
of this file.

=back

=head1 CONFIGURATION FILE

The configuration file is in xml format. Its default name is 
"regression_test.xml", but a file of another name can be used if
that name is provided on the command-line as the value for the
B<--config-file> argument.

=head2 SYNTAX

The configuration file has the following syntax:

    <regression_test>
        <cerr>
            <job> job_id number </job>
            <job> job_id number </job>
            ...
            <ignore> regexp </ignore>
            <ignore> regexp </ignore>
            ...
        </cerr>
        <srun>
            <job> job_id number </job>
            <job> job_id number </job>
            ...
            <ignore> regexp </ignore>
            <ignore> regexp </ignore>
            ...
        </srun>
    </regression_test>

=head2 SEE ALSO

Manual pages for B<regression_test.pl>

SPK System Deployment Guide. 


=head1 EXIT STATUS

Returns 0 as exit status if the recalibration was successful; otherwise
a non-zero value is returned.

=cut
