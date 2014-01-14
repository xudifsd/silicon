#!/usr/bin/perl

# filter content in file to stdout

$file = @ARGV[0];
@ARGV = ($file);

while(<>){
        s/#.*//;
        s/^\s*\.line\s.*//;
        s/^\s*$//;
        s/ +$//;
        s/^\s*\.end field//;
        print if /\S/;
}
