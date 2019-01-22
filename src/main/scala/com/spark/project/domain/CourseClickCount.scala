package com.spark.project.domain

/**
  * 实战课程点击数实体类
  * @param day_count 对应的就是HBase中的rowkey，20190121_1
  * @param click_count 对应的就是20190121_1的访问总数
  */
case class CourseClickCount(day_count:String,click_count:Long)
