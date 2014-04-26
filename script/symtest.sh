#!/bin/sh

logname=`date +%Y%m%d%H%M%S`
logfile=~/log/sym_$logname.txt
mkdir ~/log/ 2>/dev/null
>$logfile
if [ $# != 1 ]
then
    echo "Usage: $0 apklist"
else
    for i in `cat $1`
    do
        echo `date +"%Y-%m-%d %H:%M:%S"`" Begin processing $i" >>$logfile
        ./sym_onetest.sh $i 1>>$logfile 2>&1
        echo `date +"%Y-%m-%d %H:%M:%S"`" End   processing $i" >>$logfile
        echo "" >>$logfile
    done
    echo good >>$logfile
fi
