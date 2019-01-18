package com.spark.project

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

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

      }
    })
    ssc.start()
    ssc.awaitTermination()
  }
}
