#! /bin/bash
#
# poistat.sh
# (c)RW
# 2021.05.02
# Prints object statistics for targetCOUNTRY file (done by POIOSM2FS.jar by MW :) )
#
# USAGE
#	poistat.sh targetCOUNTRY	(no .txt)
#

	wc -l $1.txt > $1.STATISTIC.txt
	cat $1.txt | sed 's/.*name="//g' | sed -e 's/: .*$//g' | sort -r | uniq -c | sort -r >> $1.STATISTIC.txt
	echo
	ls -alp $1.STATISTIC.txt
	echo
	cat $1.STATISTIC.txt | more
