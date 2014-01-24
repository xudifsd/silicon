#!/usr/bin/perl
# Be careful!! no catchs.

$file = @ARGV[0];
@ARGV = ($file);
# $^I = ".bak";
$^I = "";

$value = 0;
$mapping1 = 0;
$mapping2 = 0;
$resName = 0;
while(<>){
    #s/#.*//;
    s/\r//;
    s/^\s*//;
    s/\s+$/\n/;

    # commnets
    s/^#.*//;

    # debug info
    s/^\.line.*$//;
    s/^\.local\s.+//;
    s/^\.end local.+//;
    s/^\.restart local.+//;

    # ?
    s/^\.prologue.*//;
    s/^\.end field.*//;


    # join value = {....}. Apktool
    if(/^value = {$/){
        $value = 1;
    }
    if($value == 1){
        if (/^}$/) {
            $value = 0;
        }else{
            s/ //g;
            chomp;
        }
    }
    # Our PrettyPrinter. Delete SPACE.
    if(/^value = {.*}$/){
        s/ //g;
    }


    # join mapping = {....}. Ours
    if(/^mapping = {.+/){
        $mapping2 = 1;
        s/ //g;
    }
    if($mapping2 == 1){
        if (/^.end subannotation  }$/) {
            $mapping2 = 0;
            s/ //g;
        }else{
            s/ //g;
            chomp;
        }
    }
    # join mapping = {....}. Apktool
    if(/^mapping = {$/){
        $mapping1 = 1;
        s/ //g;
    }
    if($mapping1 == 1){
        if (/^}$/) {
            $mapping1 = 0;
        }else{
            s/ //g;
            chomp;
        }
    }

    # Our PrettyPrinter. Delete SPACE.
    if(/^resName = {.+}$/){
        s/ //g;
    }
    # join value = {....}. Apktool
    if(/^resName = {$/){
        $resName = 1;
    }
    if($resName == 1){
        if (/^}$/) {
            $resName = 0;
        }else{
            s/ //g;
            chomp;
        }
    }

    if(/^parseFeatures = {.*}$/){
        s/ //g;
    }
    if(/^serialzeFeatures = {.*}$/){
        s/ //g;
    }
    if(/^ignores = {.*}$/){
        s/ //g;
    }
    if(/^orders = {.*}$/){
        s/ //g;
    }

## .catchall .catch
# s/^\.catchall.*//;
# s/^\.catch.*//;

    print if /\S/;
}
