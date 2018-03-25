package com.github.dbalduini

case class AccessLog(host: String, timestamp: String, url: String, code: String, 
  bytes: Long, rawLine: String, invalidUrl: Boolean, invalidBytes: Boolean)

object AccessLog {

  // Example log line:
  // 127.0.0.1 - - [21/Jul/2014:9:55:27 -0800] "GET /home.html HTTP/1.1" 200 2048
  // IP - - [timestamp] "request" code size
  private val LOG_ENTRY_PATTERN = """^(\S+) - - \[(.*?)\] "(.*?)" (\d{3}) (\S+)""".r

  def fromLine(line: String): Either[String, AccessLog] = {
    line match {
      case LOG_ENTRY_PATTERN(host, ts, req, code, b) =>
        val url = getUrlFromRequest(req)
        val bytes = toLong(b)
        Right(AccessLog(host, ts, url.getOrElse(req), code, bytes.getOrElse(0), line, url.isEmpty, bytes.isEmpty))
      case _ => Left(line)
    }
  }

  private def validateUrl(url: String): Boolean = url.indexOf('/') > -1

  private def getUrlFromRequest(url: String): Option[String] = {
    val i = url.indexOf("/")
    if (i > -1) {
      val subs = url.slice(i, url.length)
      return Some(subs.split(" ").head)
    }
    return None
  }

  def asLogFormat(a: AccessLog): String = "\"" + a.rawLine + "\""

  private def toLong(b: String): Option[Long] = {
    try {
      Some(b.toLong)
    } catch {
      case e: NumberFormatException => None
    }
  }
}