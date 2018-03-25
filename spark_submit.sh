#!/bin/bash
$SPARK_HOME/bin/spark-submit \
  --class "com.github.dbalduini.App" \
  --master local[4] \
  target/scala-2.11/simple-project_2.11-1.0.jar