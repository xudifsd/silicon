#!/bin/sh

if [ $# != 1 ]
then
        echo "Usage: $0 name.apk(under test/apk/)"
else
        apkname=`basename $1`
#       echo $apkname
        apkname="${apkname%.*}"
#       echo $apkname
        folder=~/result/$apkname
        apkoutput=$folder/apkoutput
        ppoutput=$folder/ppoutput

        rm -r $folder 2>/dev/null
        mkdir -p $apkoutput
        mkdir -p $ppoutput
#        echo $apkoutput
#        echo $ppoutput
#       exit;

        (cd ..
        export CLASSPATH="."
        for i in jar/*.jar
        do
                export CLASSPATH="$CLASSPATH":$i
        done

        # will generate /tmp/output/ and /tmp/smalioutput/
        java -Xmx1220m -Xms1220m -cp .:./bin:$CLASSPATH Carbon $1 -ppoutput $ppoutput -apkoutput $apkoutput

        )

        (cd $folder
        >$apkname.stdout
        >$apkname.stderr
        for i in `find $apkoutput/smali -type f`
        do
                cat $i 1>>$apkname.stdout 2>>$apkname.stderr;
        done

        >my$apkname.stdout
        >my$apkname.stderr
        for i in `find $ppoutput -type f`
        do
                cat $i 1>>my$apkname.stdout 2>>my$apkname.stderr;
        done

        perl ~/carbon/script/format.pl $apkname.stdout
        perl ~/carbon/script/format.pl my$apkname.stdout
        diff $apkname.stdout my$apkname.stdout 1>$apkname.diff 2>$apkname.diff.err
        ls -l $apkname.diff
        )
fi