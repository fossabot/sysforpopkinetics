#!/usr/bin/perl -w

use strict;

use Spkdb ('connect', 'disconnect', 'new_job', 'new_user');

my $schema = shift;

my $rv;

my $tmp_name = "/tmp/junk$$";
my $admin = ".";
my $drop   = "$admin/drop.sql";
system "echo 'use spktest;' > $tmp_name";
system "cat $tmp_name $drop   | mysql --force -ptester -utester > /dev/null 2>&1";
system "cat $tmp_name $schema | mysql --force -ptester -utester";
system "rm $tmp_name";

exit 0;