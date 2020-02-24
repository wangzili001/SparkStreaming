package com.vshare.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * groupBy算子
 */
object Spark_Opera6 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("Spark_Opera6").setMaster("local[*]")
    val sc = new SparkContext(sparkConf)
    //groupBy算子  按照指定规则进行过滤
    //将一个RDD {1，2，3，4，5，6，7，8，9，10} *2 变成一个新的RDD
    val RDD: RDD[Int] = sc.makeRDD(List(1,2,3,4,5,6),3)
    val result: RDD[(Int, Iterable[Int])] = RDD.groupBy(i=>i%2)
    result.collect().foreach(println)
  }
}
