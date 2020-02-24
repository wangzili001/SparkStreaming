package com.vshare.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark_RDD1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("Spark_RDD1")
    val sc = new SparkContext(sparkConf)
    //创建RDD
    //1.1:从内存中创建
    val listRDD: RDD[Int] = sc.makeRDD(List(1,2,3,4))
//    listRDD.collect().foreach(println)
    //1.2:从内存中创建parallelize
    val arrayRDD: RDD[Int] = sc.parallelize(Array(1,2,3,4,5))
//    arrayRDD.collect().foreach(println)
    //2 从外部文件中创建
    val textRDD: RDD[String] = sc.textFile("in")
    listRDD.saveAsTextFile("makeRDD")
  }
}
