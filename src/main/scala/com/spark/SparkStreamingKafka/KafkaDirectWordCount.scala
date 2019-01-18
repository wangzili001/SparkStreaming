package com.spark.SparkStreamingKafka

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * SparkStreaming对接Kafka方式二Direct
  */
object KafkaDirectWordCount {
  def main(args: Array[String]): Unit = {
    if(args.length !=2){
      System.err.print("Usage:KafkaDirectWordCount<brokers><topics>")
      System.exit(1)
    }
    val Array(brokers,topics) = args
    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("KafkaDirectWordCount")
    val ssc = new StreamingContext(sparkConf,Seconds(5))

    val kafkaParams = Map[String,String]("metadata.broker.list"->brokers)
    val topicsSet = topics.split(",").toSet
    //SparkStreaming如何对接kafka
    val messages = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](ssc,kafkaParams,topicsSet)
    //查看为什么要取第二个
    messages.print()

    messages.map(_._2).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
