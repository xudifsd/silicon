#!/bin/sh

if [ $# != 1 ]
then
        echo "Usage: $0 apklist"
else
        for i in `head -2 $1`
        do
                ./onetest.sh $i
                #echo $i
        done
fi