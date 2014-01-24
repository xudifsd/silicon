#!/bin/sh

carbon_panic () {
    echo carbon_panic
    > $1/carbon_panic
    exit 1
}

package_panic () {
    echo package_panic
    > $1/package_panic
    exit 1
}

if [ $# != 2 ] || [ $2 != "ast" -a $2 != "sim" ]
then
    echo "Usage: $0 path/to/name.apk [ast|sim] "
else
    dump=$2
    apkname=`basename $1`
    apkname="${apkname%.*}"
    folder=~/result_${dump}/$apkname
    apkoutput=$folder/apkoutput
    ppoutput=$folder/ppoutput
    dest=$folder/my$apkname

    rm -r $folder 2>/dev/null
    mkdir -p $apkoutput
    mkdir -p $ppoutput

    (cd ..
        export CLASSPATH="."
        for i in jar/*.jar
        do
            export CLASSPATH="$CLASSPATH":$i
        done

        java -Xmx1220m -Xms1220m -cp .:./bin:$CLASSPATH Carbon $1 -ppoutput $ppoutput -apkoutput $apkoutput -dump $dump >$folder/carbon.stdout 2>$folder/carbon.stderr || carbon_panic $folder
    )

    if [ -e $folder/carbon_panic ]
    then
        exit 1
    else
        # get diff
        (
            (
            >$folder/$apkname.apk.smali
            for i in `find $apkoutput/smali -type f`
            do
                cat $i >>$folder/$apkname.apk.smali
            done
            perl format.pl $folder/$apkname.apk.smali
            ) &

            (
            >$folder/$apkname.pp.smali
            for i in `find $ppoutput -type f`
            do
                cat $i >>$folder/$apkname.pp.smali
            done
            perl format.pl $folder/$apkname.pp.smali
            ) &

        wait
        diff $folder/$apkname.apk.smali $folder/$apkname.pp.smali 1>$folder/$apkname.diff 2>$folder/$apkname.diff.stderr
        ls -l $folder/$apkname.diff
        ) &

        # package it
        (cd ..
            mkdir $dest
            # only copy needed file, ignore $apkoutput/smali
            for i in `find $apkoutput -maxdepth 1|grep -v smali| tail -n +2`
            do
                cp -r $i $dest
            done
            cp -r $ppoutput $dest/smali

            paout=$folder/package.stdout
            paerr=$folder/package.stderr
            > $paout
            > $paerr
            (
                java -jar jar/apktool.jar b $dest >> $paout 2>> $paerr &&
                java -jar jar/SignAPK.jar $dest/dist/$apkname.apk $dest.apk >> $paout 2>> $paerr
            ) || package_panic $folder
        )
        wait
    fi
fi
