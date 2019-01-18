package com.spark.SparkStreamingFlume

import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * SparkStreaming整合Flume的第一种方式
  */
object FlumePullWordCount {
  def main(args: Array[String]): Unit = {

    if(args.length != 2){
      System.err.print("Usage: FlumePullWordCount <hostname><port>")
      System.exit(1)
    }

    val Array(hostname,port) = args

    val sparkConf = new SparkConf()
    //val sparkConf = new SparkConf().setMaster("local[2]").setAppName("FlumePullWordCount")
    val ssc = new StreamingContext(sparkConf,Seconds(5))
    //如何使用SparkStreaming整合Flume
    val flumeStream = FlumeUtils.createPollingStream(ssc,hostname,port.toInt)

    flumeStream.map(x=>new String(x.event.getBody.array()).trim)
      .flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
