package com.spark.project.domain

/**
  * 清洗后的日志信息
  * @param ip 日志访问ip
  * @param time 日志访问时间
  * @param courseId 日志访问的实战课程编号
  * @param statusCOde 日志访问状态码
  * @param referer 日志访问的referer
  */
case class ClickLog(ip:String,time:String,courseId:Int,statusCode:Int,referer:String) {

}
