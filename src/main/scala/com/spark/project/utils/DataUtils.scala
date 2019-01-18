package com.spark.project.utils

import java.sql.Date

import org.apache.commons.lang3.time.FastDateFormat


/**
  * 日期时间工具类
  */
object DataUtils {

  val YYYYMMDDHHMMSS_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")
  val TARGE_FORMAT = FastDateFormat.getInstance("yyyyMMddHHmmss")

  def getTime(time: String)={
    YYYYMMDDHHMMSS_FORMAT.parse(time).getTime
  }

  def parseToMinute(time: String)={
    TARGE_FORMAT.format(new Date(getTime(time)))
  }

  def main(args: Array[String]): Unit = {
    print(parseToMinute("2019-01-18 15:37:01"))
  }
}
