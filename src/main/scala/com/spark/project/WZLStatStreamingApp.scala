package com.spark.project

import com.spark.project.dao.{CourseClickCountDAO, CourseSearchClickCountDAO}
import com.spark.project.domain.{ClickLog, CourseClickCount, CourseSearchClickCount}
import com.spark.project.utils.DateUtils
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

object WZLStatStreamingApp {
  def main(args: Array[String]): Unit = {

    if(args.length !=4){
      println("Usage: WZLStatStreamingApp<zkQuorum> <groupId> <topicMap> <numThread>")
      System.exit(1)
    }
    val Array(zkQuorum,groupId,topics,numThread) = args

    val sparkConf = new SparkConf().setAppName("WZLStatStreamingApp").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf,Seconds(60))

    val topicMap = topics.split(",").map((_,numThread.toInt)).toMap


    val messages = KafkaUtils.createStream(ssc,zkQuorum,groupId,topicMap)
    //测试步骤一：测试数据接收
//   messages.map(_._2).count().print()
    //测试步骤二：数据清洗
    val logs = messages.map(_._2)
    val cleanData = logs.map(line => {
      val infos = line.split("\t")
      //infos(2) = "GET /class/145.html HTTP/1.1"
      val url = infos(2).split(" ")(1)
      var courseId = 0

      if(url.startsWith("/class")){
        val courseIdHTML = url.split("/")(2)
        courseId = courseIdHTML.substring(0,courseIdHTML.lastIndexOf(".")).toInt
      }
        ClickLog(infos(0),DateUtils.parseToMinute(infos(1)),courseId,infos(3).toInt,infos(4))
    }).filter(clicklog => clicklog.courseId != 0)

    //测试步骤三：统计今天到现在为止实战课程的访问量
    cleanData.map(x =>{
      //HBase rowkey设计： 20190122_11

      (x.time.substring(0,8)+"_"+x.courseId,1)
    }).reduceByKey(_+_).foreachRDD(rdd => {
      rdd.foreachPartition(partitionRecords =>{
        val list = new ListBuffer[CourseClickCount]

        partitionRecords.foreach(pair => {
          list.append(CourseClickCount(pair._1,pair._2))
        })
        CourseClickCountDAO.save(list)
      })
    })

    //测试步骤三：统计今天到现在为止从搜索引擎引流过来的实战课程的访问量
    cleanData.map(x =>{
      val referer = x.referer.replace("//","/")
      val splits = referer.split("/")
      var host = ""
      if(splits.length > 2){
        host =splits(1)
      }
      (host,x.courseId,x.time)
    }).filter(_._1 != "").map(x => {
      (x._3.substring(0,8)+"_"+x._1+"_"+x._2,1)
    }).reduceByKey(_+_).foreachRDD(rdd => {
      rdd.foreachPartition(partitionRecords =>{
        val list = new ListBuffer[CourseSearchClickCount]

        partitionRecords.foreach(pair => {
          list.append(CourseSearchClickCount(pair._1,pair._2))
        })
        CourseSearchClickCountDAO.save(list)
      })
    })
    ssc.start()
    ssc.awaitTermination()
  }
}
