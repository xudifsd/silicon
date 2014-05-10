#!/bin/sh

silicon_panic () {
    echo silicon_panic
    > $1/silicon_panic
    exit 1
}

if [ $# != 1 ]
then
    echo "Usage: $0 path/to/name.apk"
else
    apkname=`basename $1`
    apkname="${apkname%.*}"
    folder=~/sym_result_2/$apkname
    apkoutput=$folder/apkoutput
    symoutput=$folder/symoutput.txt

    rm -r $folder 2>/dev/null
    mkdir -p $apkoutput

    (cd ..
        export CLASSPATH="."
        for i in jar/*.jar
        do
            export CLASSPATH="$CLASSPATH":$i
        done

        timeout -s SIGKILL 15m java -Xmx1520m -Xms1520m -cp .:./bin:$CLASSPATH Silicon $1 -apkoutput $apkoutput -action sym -symoutput $symoutput >$folder/silicon.stdout 2>$folder/silicon.stderr || silicon_panic $folder
    )

    if [ -e $folder/silicon_panic ]
    then
        exit 1
    fi
fi
