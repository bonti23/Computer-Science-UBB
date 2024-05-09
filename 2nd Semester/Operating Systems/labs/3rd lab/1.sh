#!/bin/bash

#a is going to be like a list which contains the users who are logged in
a=$(cat who.fake.txt | awk '{print $1}')

for argv in $a
do
        #b is going to be the number of processes
        b=$(cat ps.fake.txt | awk '{print $1}' | grep $argv | wc -l)
        echo $argv $b
done
