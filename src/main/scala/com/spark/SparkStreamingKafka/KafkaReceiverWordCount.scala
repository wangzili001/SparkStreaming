package com.spark.SparkStreamingKafka

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * SparkStreaming对接Kafka方式一 Receiver
  */
object KafkaReceiverWordCount {
  def main(args: Array[String]): Unit = {
    if(args.length !=4){
      System.err.print("Usage:KafkaReceiverWordCount<zkQuorum><group><topics><numThreads>")
    }
    val Array(zkQuorum,group,topics,numThreads) = args
    val sparkConf = new SparkConf()//.setMaster("local[2]").setAppName("KafkaReceiverWordCount")
    val ssc = new StreamingContext(sparkConf,Seconds(5))

    val topicMap = topics.split(",").map((_,numThreads.toInt)).toMap
    //SparkStreaming如何对接kafka
    val messages = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap)
    //查看为什么要取第二个
    messages.print()

    messages.map(_._2).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
