package com.vshare.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * mapPartition算子
 */
object Spark_Opera2 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("Spark_Opera2").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    //mapPartition算子
    //将一个RDD {1，2，3，4，5，6，7，8，9，10} *2 变成一个新的RDD
    val RDD1: RDD[Int] = sc.makeRDD(1 to 10)
    //mapPartition可以对一个RDD中所有的分区进行操作  下面的mao是scala中的map  不是spark中的map
    //mapPartition效率要高于map算子。减少了发送到执行器的次数
    //mapPartition可能会出现内存溢出
    val mapPartitionsRDD: RDD[Int] = RDD1.mapPartitions(datas=>{datas.map((_*2))})
    mapPartitionsRDD.collect().foreach(println)
  }
}
