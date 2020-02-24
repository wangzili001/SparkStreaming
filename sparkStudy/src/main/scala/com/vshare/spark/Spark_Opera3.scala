package com.vshare.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * mapPartition算子
 */
object Spark_Opera3 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("Spark_Opera3").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    //mapPartition算子
    //将一个RDD {1，2，3，4，5，6，7，8，9，10} *2 变成一个新的RDD
    val RDD1: RDD[Int] = sc.makeRDD(1 to 10,2)

    val mapPartitionsWithIndexRDD: RDD[(Int, String)] = RDD1.mapPartitionsWithIndex {
      case (num, datas) => {
        datas.map((_, ",分区号:" + num))
      }
    }
    mapPartitionsWithIndexRDD.collect().foreach(println)
  }
}
