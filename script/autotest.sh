#!/bin/sh

logname=`date +%Y%m%d%H%M%S`
logfile=~/log/$logname.txt
mkdir ~/log/ 2>/dev/null
>$logfile
if [ $# != 2 ] || [ $2 != "ast" -a $2 != "sim"]
then
    echo "Usage: $0 apklist [ast|sim]"
else
    dump=$2
    for i in `cat $1`
    do
        echo `date +"%Y-%m-%d %H:%M:%S"`" Begin processing $i $dump" >>$logfile
        ./onetest.sh $i $dump 1>>$logfile 2>&1
        echo `date +"%Y-%m-%d %H:%M:%S"`" End   processing $i $dump" >>$logfile
        echo "" >>$logfile
    done
    echo good >>$logfile
fi
