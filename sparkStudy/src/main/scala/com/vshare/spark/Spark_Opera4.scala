package com.vshare.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * flatMap算子
 */
object Spark_Opera4 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("Spark_Opera4").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    //mapPartition算子
    //将一个RDD {1，2，3，4，5，6，7，8，9，10} *2 变成一个新的RDD
    val RDD: RDD[List[Int]] = sc.makeRDD(Array(List(1,2,3),List(2,3,4)))

    val result: RDD[Int] = RDD.flatMap(x=>x)
    result.collect().foreach(println)
  }
}
