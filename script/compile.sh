#!/bin/bash

(cd ..
    rm -rf bin/ 2> /dev/null
    mkdir bin
    (cd src/antlr3 && java -jar ../../jar/antlr-3.2.jar TranslateWalker.g)
    export CLASSPATH="."
    for i in jar/*.jar
    do
        export CLASSPATH="$CLASSPATH":$i
    done
    find src/ -type f -regex ".*\.java" | xargs javac -classpath bin:$CLASSPATH -d bin
)
