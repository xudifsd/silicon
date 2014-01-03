#!/bin/bash

declare -a categoryName
categoryName[1]=InputMethod
categoryName[2]=Broswer
categoryName[3]=WallPaper
categoryName[4]=SystemTool
categoryName[5]=LifeTool
categoryName[6]=MediaPlayer
categoryName[7]=Communicate
categoryName[8]=SocialNetwork
categoryName[9]=ThemePlugin
categoryName[10]=Camera
categoryName[11]=News
categoryName[12]=Reading
categoryName[13]=Study
categoryName[14]=Shopping
categoryName[15]=Financial
categoryName[16]=Game

for category in {1..9}
do
	mkdir ${category}_${categoryName[$category]}_APK
	cd ${category}_${categoryName[$category]}_APK
	for page in {1..3}
	do
		 wget -q -O- http://www.appchina.com/category/30${category}/1_1_${page}_1_0_0_0.html | grep -o -i  http://www.appchina.com/market/[^\']* | head -18     >>${categoryName[$category]}.txt
		 wget -q -O- http://www.appchina.com/category/30${category}/1_1_${page}_1_0_0_0.html | egrep -o -i  'app_name\sch.+' |head -18 | sed -e "s/.\+>\(.\+\)<\/a>.\+/\1/" >>${categoryName[$category]}_Name.txt
	done
	for i in `cat ${categoryName[$category]}.txt`
	do
		name=`echo $i| sed -e "s/.\+\([0-9]\+\)\/\(.\+\)?.\+/\2/"`
		wget "$i" -O $name
	done
	cd ..
done
#10 to 15...becasue of the url...
for category in {10..15}
do
	mkdir ${category}_${categoryName[$category]}_APK
	cd ${category}_${categoryName[$category]}_APK

	for page in {1..3}
	do
		if 

		 wget -q -O- http://www.appchina.com/category/3${category}/1_1_${page}_1_0_0_0.html | grep -o -i  http://www.appchina.com/market/[^\']* | head -18     >>${categoryName[$category]}.txt
		 wget -q -O- http://www.appchina.com/category/3${category}/1_1_${page}_1_0_0_0.html | egrep -o -i  'app_name\sch.+' |head -18 | sed -e "s/.\+>\(.\+\)<\/a>.\+/\1/" >>${categoryName[$category]}_Name.txt
	done
	for i in `cat ${categoryName[$category]}.txt`
	do
		name=`echo $i| sed -e "s/.\+\([0-9]\+\)\/\(.\+\)?.\+/\2/"`
		wget "$i" -O $name
	done
	cd ..
done

#game is special
mkdir $16_${categoryName[16]}_APK
cd $16_${categoryName[16]}_APK

for page in {1..3}
do
	if 

	 wget -q -O- http://www.appchina.com/category/40/1_1_${page}_1_0_0_0.html | grep -o -i  http://www.appchina.com/market/[^\']* | head -18     >>${categoryName[16]}.txt
	 wget -q -O- http://www.appchina.com/category/40/1_1_${page}_1_0_0_0.html | egrep -o -i  'app_name\sch.+' |head -18 | sed -e "s/.\+>\(.\+\)<\/a>.\+/\1/" >>${categoryName[16]}_Name.txt
done
for i in `cat ${categoryName[16]}.txt`
do
	name=`echo $i| sed -e "s/.\+\([0-9]\+\)\/\(.\+\)?.\+/\2/"`
	wget "$i" -O $name 
done
cd ..




