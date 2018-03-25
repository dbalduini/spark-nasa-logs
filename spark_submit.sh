#!/bin/bash

if [ -z $1 ]
	then
		echo "Usage: ./spark-submit <n_cores>"
		exit 1
fi

$SPARK_HOME/bin/spark-submit \
  --class "com.github.dbalduini.App" \
  --master local[$1] \
  dist/spark-nasa-logs_2.11-1.0.jar