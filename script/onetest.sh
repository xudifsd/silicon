#!/bin/sh

panic () {
    echo panic
    > $1/panic
    exit 1
}

if [ $# != 1 ]
then
    echo "Usage: $0 name.apk"
else
    apkname=`basename $1`
    apkname="${apkname%.*}"
    folder=~/result/$apkname
    apkoutput=$folder/apkoutput
    ppoutput=$folder/ppoutput

    rm -r $folder 2>/dev/null
    mkdir -p $apkoutput
    mkdir -p $ppoutput

    (cd ..
    export CLASSPATH="."
    for i in jar/*.jar
    do
        export CLASSPATH="$CLASSPATH":$i
    done

    java -Xmx1220m -Xms1220m -cp .:./bin:$CLASSPATH Carbon $1 -ppoutput $ppoutput -apkoutput $apkoutput >$folder/carbon.stdout 2>$folder/carbon.stderr || panic $folder
    )

    if [ -e $folder/panic ]
        exit 1
    else
        (cd $folder
        >$apkname.apk.smali
        for i in `find $apkoutput/smali -type f`
        do
            cat $i >>$apkname.apk.smali
        done

        >$apkname.pp.smali
        for i in `find $ppoutput -type f`
        do
            cat $i >>$apkname.pp.smali
        done

        perl ~/carbon/script/format.pl $apkname.apk.smali
        perl ~/carbon/script/format.pl $apkname.pp.smali
        diff $apkname.apk.smali $apkname.pp.smali 1>$apkname.diff 2>$apkname.diff.stderr
        ls -l $apkname.diff
        )
    fi
fi
