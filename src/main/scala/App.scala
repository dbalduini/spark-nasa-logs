package com.github.dbalduini

import java.nio.charset.StandardCharsets
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd.RDD
import org.apache.log4j.{ Level, Logger }

object App {

  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder()
      .appName("Simple Application")
      .getOrCreate()

    val dataset = initDataset(spark)

    val nRows = dataset.count()
    println(s"Total rows in the dataset: $nRows")

    // ~373MB fits into memory
    dataset.cache()

    // Map string lines to AccessLog objects
    val parsedLines = dataset.map(AccessLog.fromLine _)
    val notParsedLines = parsedLines.filter(_.isLeft).map { case Left(s) => s }
    val accessLogs = parsedLines.filter(_.isRight).map { case Right(a) => a }

    // 1. Checking unique hosts
    val uniqueHosts = accessLogs.map(_.host).distinct()
    println(s"Number of unique hosts: ${uniqueHosts.count()}")

    // 2. Total 404 errors
    val notFoundRequests = accessLogs.filter(_.code == "404")
    println(s"Total 404 errors: ${notFoundRequests.count()}")

    // 3. Top five URLs with most 404 errors
    val countNotFoundRequests = notFoundRequests.map(log => (log.url, 1)).reduceByKey(_ + _)
    val top404erros = countNotFoundRequests.sortBy(p => p._2, ascending = false).take(5)
    println(s"Top 5 urls: ${top404erros.mkString(",")}")

    // 4. Total 404 errors by day
    val notFoundRequestsByDay = notFoundRequests.map(toPairs).reduceByKey(_ + _)
    val totalDays: Double = notFoundRequestsByDay.count()
    val avarageNotFoundErros = notFoundRequestsByDay.map(t => t._2 / totalDays).sum
    printf(s"Average not found erros by day: %.2f\n", avarageNotFoundErros)

    // 5. Total bytes returned
    val totalBytes = accessLogs.map(_.bytes).reduce(_ + _)
    println(s"Total bytes returned: $totalBytes")
    // res: 65524314915
    // ~ 65.5GB

    spark.stop()
  }

  def initDataset(spark: SparkSession): RDD[String] = {
    val projectDir = System.getProperty("user.dir")

    println(s"Project directory: $projectDir")

    val data_aug95 = spark
      .read
      .option("encoding", StandardCharsets.US_ASCII.name())
      .textFile(projectDir + "/NASA_access_log_Aug95")

    val data_jul95 = spark
      .read
      .option("encoding", StandardCharsets.US_ASCII.name())
      .textFile(projectDir + "/NASA_access_log_Jul95")

    data_jul95.union(data_aug95).rdd
  }

  def getDayOfMonth(s: String): LocalDate = {
    val formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z")
    LocalDate.parse(s, formatter)
  }

  def toPairs = (a: AccessLog) => (getDayOfMonth(a.timestamp), 1)

}
