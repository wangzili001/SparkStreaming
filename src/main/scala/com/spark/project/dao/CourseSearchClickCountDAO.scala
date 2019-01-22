package com.spark.project.dao

import com.spark.project.domain.{CourseClickCount, CourseSearchClickCount}
import com.spark.project.utils.HBaseUtils
import org.apache.hadoop.hbase.client.Get
import org.apache.hadoop.hbase.util.Bytes

import scala.collection.mutable.ListBuffer

/**
  * 实战课程点击数数据访问层
  */
object CourseSearchClickCountDAO {

  val tableName = "wzl_course_search_clickcount"
  val cf = "info"
  val qualifer = "click_count"

  /**
    * 保存数据到HBase
    * @param List CourseSearchClickCount
    */
  def save(list: ListBuffer[CourseSearchClickCount]): Unit={

    val table = HBaseUtils.getInstance().getTable(tableName)
    for (ele <- list){
      table.incrementColumnValue(Bytes.toBytes(ele.day_search_course),
        Bytes.toBytes(cf),
        Bytes.toBytes(qualifer),
        ele.click_count
      )
    }
  }

  /**
    * 根据rowKey查询值
    * @param day_count
    * @return
    */
  def count(day_search_course:String):Long = {
    val table = HBaseUtils.getInstance().getTable(tableName)

    val get = new Get(Bytes.toBytes(day_search_course))
    val value = table.get(get).getValue(cf.getBytes(),qualifer.getBytes())
    if(value == null) {
      0l
    }else Bytes.toLong(value)
  }

  def main(args: Array[String]): Unit = {
    val list = new ListBuffer[CourseSearchClickCount]
    list.append(CourseSearchClickCount("20190222_www.baidu.com_8",2l))
    list.append(CourseSearchClickCount("20190222_www.sougou.com_9",3l))
    list.append(CourseSearchClickCount("20190222_cn.bing.com_10",4l))
    list.append(CourseSearchClickCount("20190222_search.yahoo.com_11",4l))
//    save(list)
    println(count("20190222_www.baidu.com_8")+count("20190222_www.sougou.com_9")
      +count("20190222_cn.bing.com_10")+count("20190222_search.yahoo.com_11"))
  }
}
