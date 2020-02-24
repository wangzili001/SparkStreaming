package com.vshare.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * map算子
 */
object Spark_Opera1 {
  def main(args: Array[String]): Unit = {
      val sparkConf = new SparkConf().setAppName("Spark_Opera1").setMaster("local[2]")
    val sc = new SparkContext(sparkConf)
    //map算子
    //将一个RDD {1，2，3，4，5，6，7，8，9，10} *2 变成一个新的RDD
    val RDD1: RDD[Int] = sc.makeRDD(1 to 10 )
    val RDD2: RDD[Int] = RDD1.map((_*2))
    RDD2.collect().foreach(println)
  }
}
