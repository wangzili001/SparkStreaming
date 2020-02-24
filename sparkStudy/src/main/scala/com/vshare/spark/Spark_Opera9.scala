package com.vshare.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * distinct算子
 */
object Spark_Opera9 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("Spark_Opera8").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    //distinct算子  将数据打乱重组  参数表示放几个分区里面
    //distinct算子对数据去重 但是去重后数据变少 所以可以改变默认分区大小
    //将rdd中一个分区的数据打乱重组到其他的分区的操作，称之为shuffle操作
    val RDD: RDD[Int] = sc.makeRDD(List(1,8,3,4,3,3,5,6),3)
    val result: RDD[Int] = RDD.distinct(2)
    result.saveAsTextFile("out")
  }
}
