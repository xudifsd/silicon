#!/bin/sh

(cd ..
    folder="${1%.*}"
    folder=my$folder
    dest=/tmp/

    rm -r smalioutput 2>/dev/null
    rm -r output 2>/dev/null
    rm -r /tmp/output 2>/dev/null
    rm -r $dest$folder

    # will generate output/ and smalioutput/
    java -Xmx2g -Xms2g -cp .:./bin:$CLASSPATH Carbon ./test/apk/$1

    cp -r output /tmp/
    mv  output $dest$folder
    rm -r  $dest$folder/smali
    mv smalioutput $dest$folder/smali
    java -jar jar/apktool.jar b $dest$folder

    java -jar jar/SignAPK.jar $dest$folder/dist/$1  $dest${folder}.apk
)
