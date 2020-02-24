package com.vshare.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * coalesce算子
 */
object Spark_Opera10 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("Spark_Opera8").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    //coalesce算子 缩减分区数 用于大数据过滤后 提高小数据集的处理效率
    val RDD: RDD[Int] = sc.makeRDD(1 to 16,4)
    val result: RDD[Int] = RDD.coalesce(3)
    result.saveAsTextFile("out")
  }
}
