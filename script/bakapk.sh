#!/bin/sh

if [ $# != 1 ]
then
	echo "Usage: $0 name.apk(under test/apk/)"
else
	(cd ..
	folder="${1%.*}"
	folder=my$folder
	dest=/tmp/$folder

	rm -r /tmp/output 2>/dev/null
	rm -r /tmp/smalioutput 2>/dev/null
	rm -r $dest 2>/dev/null

	export CLASSPATH="."
	for i in jar/*.jar
	do
		export CLASSPATH="$CLASSPATH":$i
	done

	# will generate /tmp/output/ and /tmp/smalioutput/
	java -Xmx2g -Xms2g -cp .:./bin:$CLASSPATH Carbon ./test/apk/$1

	mkdir $dest
	# only copy needed file, ignore /tmp/output/smali
	for i in `find /tmp/output/ -maxdepth 1|grep -v smali| tail -n +2`
	do
		cp -r $i $dest
	done
	mv /tmp/smalioutput $dest/smali
	java -jar jar/apktool.jar b $dest

	java -jar jar/SignAPK.jar $dest/dist/$1 $dest.apk
	)
fi
