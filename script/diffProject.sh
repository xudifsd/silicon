#!/bin/bash

function filter {
    egrep "(#.*|.line [0-9]*|.end field|.prologue)" --no-filename -Rv $1
}

if [ $# != "2" ]
then
    echo "Usage: $0 dir1 dir2"
else
    diff --ignore-all-space --ignore-blank-lines --suppress-common-lines <(filter $1) <(filter $2)
fi
