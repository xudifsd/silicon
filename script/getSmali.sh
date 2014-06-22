#!/bin/bash

(cd ../test/tmp
    rm -rf /tmp/output /tmp/smalioutput 2> /dev/null
    /Library/Java/JavaVirtualMachines/jdk1.7.0_45.jdk/Contents/Home/bin/javac *.java
    java -jar ../../jar/dx.jar --dex --output classes.dex *.class
    zip tmp classes.dex
    mv tmp.zip tmp.apk
    cd ../..
    java -cp "bin/:jar/antlr-3.2.jar:jar/apktool.jar:jar/antlr-3.2.jar:jar/clojure-1.5.1.jar" Silicon test/tmp/tmp.apk
)
