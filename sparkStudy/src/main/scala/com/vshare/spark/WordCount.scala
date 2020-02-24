package com.vshare.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    //使用开发工具完成spark wordCount开发
    //local模式
    //创建sparkconf对象
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    //创建spark上下文

    var sc = new SparkContext(sparkConf)
    //读取文件
    val lines: RDD[String] = sc.textFile("D:\\wzl\\SparkStreaming\\sparkStudy\\in")

    val result: Array[(String, Int)] = lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).collect()
    println(result.mkString(","))
  }
}
