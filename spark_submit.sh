#!/bin/bash
$SPARK_HOME/bin/spark-submit \
  --class "com.github.dbalduini.App" \
  --master local[4] \
  dist/spark-nasa-logs_2.11-1.0.jar