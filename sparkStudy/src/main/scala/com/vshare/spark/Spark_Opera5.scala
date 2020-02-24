package com.vshare.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * glom算子
 */
object Spark_Opera5 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("Spark_Opera5").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    //glom算子  将一个分区中的数据形成一个数组
    //将一个RDD {1，2，3，4，5，6，7，8，9，10} *2 变成一个新的RDD
    val RDD: RDD[Int] = sc.makeRDD(1 to 16,4)

    val result: RDD[Array[Int]] = RDD.glom()
    result.collect().map(x=>println(x.mkString(",")))
  }
}
