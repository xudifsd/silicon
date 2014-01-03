#!/bin/bash
# download apk from page 1 to 10 in wandoujia.com
rm downlist.txt
for page in `seq 10`
do
	wget -q -O- http://www.wandoujia.com/tag/app?page=${page} | grep -o -i  http://www.wandoujia.com/apps/[^\"]*  | uniq  |sed  "s/www/apps/g" |sed  "s/$/&\/qrbinded?pos=www\/sem\/bdpmt_exact/g" >>downlist.txt
done
for i in `cat downlist.txt`
do
	 echo $i
	name=`echo $i|cut -d '/' -f 5`
	wget  "$i" -O ${name}.apk
done



