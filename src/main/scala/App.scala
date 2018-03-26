package com.github.dbalduini

import java.nio.charset.StandardCharsets
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd.RDD
import org.apache.log4j.{ Level, Logger }

object App {

  private val formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z")

  def main(args: Array[String]) {
    // Configure Spark logging Level
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession
      .builder()
      .appName("Spark Nasa Logs")
      .getOrCreate()

    val dir = System.getProperty("user.dir")
    println(s"Project directory: $dir")
    val dataset = loadDataset(dir, spark)

    // ~373MB fits into memory
    dataset.cache()

    val nRows = dataset.count()
    println(s"\nTotal rows in the dataset: $nRows")

    // Map string lines to AccessLog objects
    val parsedLines = dataset.map(AccessLog.fromLine _)

    // Filter only successfully parsed lines
    val accessLogs = parsedLines.filter(_.isRight).map(_.right.get)

    // 1. Check unique hosts
    val uniqueHosts = accessLogs.map(_.host).distinct()
    println(s"Number of unique hosts: ${uniqueHosts.count()}")

    // 2. Total 404 errors
    val notFoundRequests = accessLogs.filter(_.code == "404")
    println(s"Total 404 errors: ${notFoundRequests.count()}")

    // 3. Top five URLs with most 404 errors
    val countNotFoundRequests = notFoundRequests
      .map(log => (log.url, 1))
      .reduceByKey(_ + _)

    val top404erros = countNotFoundRequests
      .sortBy(p => p._2, ascending = false)
      .take(5)

    println(s"\nTop 5 urls with most 404 errors: \n${top404erros.mkString("\n")}\n")

    // 4. Total 404 errors by day
    def getDayOfMonth = (s: String) => LocalDate.parse(s, formatter)

    val notFoundRequestsByDay = notFoundRequests
      .map(log => (getDayOfMonth(log.timestamp), 1))
      .reduceByKey(_ + _)

    val averageNotFoundErros = notFoundRequestsByDay.map(_._2).mean()

    printf(s"Average not found erros by day: %.2f\n", averageNotFoundErros)

    // 5. Total bytes returned
    val totalBytes = accessLogs.map(_.bytes).reduce(_ + _)
    println(s"Total bytes returned: $totalBytes")

    spark.stop()
  }

  def loadDataset(dir: String, spark: SparkSession): RDD[String] = {
    val encoding = StandardCharsets.US_ASCII.name()
    val data1 = spark.read.option("encoding", encoding).textFile(dir + "/NASA_access_log_Aug95")
    val data2 = spark.read.option("encoding", encoding).textFile(dir + "/NASA_access_log_Jul95")
    data1.union(data2).rdd
  }

}
