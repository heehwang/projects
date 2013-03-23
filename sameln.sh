#!/bin/bash
 
#Variable DIR gets the value of argument 1.
DIR=$1;
#find the file under the $DIR, extract the file name, and sort.
RESULT=`find $DIR -maxdepth 1 -type f | sed 's/.*\///' | sort -u`


#Represent the files.
echo "$RESULT" | od -c

#Create an ARRAY which contains the regular file under the directory.
declare -a ARRAY
let count=0;


#Set IFS(Internal Field Separator) to '\n' for the file with spaces.
IFS=$'\n'
for FILE in $RESULT
do
#It stores the FILE in the ARRAY.
        ARRAY[$count]=$FILE;
        ((count++));
done 
unset IFS

#Count the number of files assigned to array
echo $count

#Compare the elements in the ARRAY.
for((i=0; i <= $count-1; i++))
do
    FILE1=${ARRAY[$i]}
    for((j=i+1; j <= $count-1; j++))
    do
    FILE2=${ARRAY[$j]}
#If FILE1 and FILE2 have the same contents and different name, 
#FILE2 becomes a hard link of FILE1
#Use the exit status of cmp(0:identical)
#FILE1 and FILE2 should be double-quoted not to expand a wild card like "*"
#"--" delimits the option, which enables to use file name starts with "-"
    cmp -s -- "$FILE1" "$FILE2"
    ret_value=$?
        if [ $ret_value -eq 0 ]
        then
	    echo "$FILE2" is changing to Hardlink of "$FILE1"
	    ln -f -- "$FILE1" "$FILE2"
	    ((comp_count++));
	else
	    ((comp_count++));
	fi
    done
done

# Numbers of comparison between two files
echo $comp_count

