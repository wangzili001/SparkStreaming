package com.spark.SparkStreaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StatefulWordCount {

   def main(args: Array[String]): Unit = {
     val sparkConf = new SparkConf().setAppName("StatefulWordCount")setMaster("local[2]")

     val ssc = new StreamingContext(sparkConf,Seconds(5))

     /**如果使用了带状态的算子必须要设置checkpoint，
       * 这里是设置在HDFS的文件夹中
       */
     ssc.checkpoint(".")

     val lines = ssc.socketTextStream("192.168.1.138",6789)

     val result = lines.flatMap(_.split(" ")).map((_,1))

     val state = result.updateStateByKey[Int](updateFunction _)

     state.print()

     ssc.start()
     ssc.awaitTermination()

   }

  /**
    * 把当前的数据去更新已有的数据
    * currentValues: Seq[Int] 新有的状态
    * preValue: Option[Int] 已有的状态
    * */
  def updateFunction(currentValues: Seq[Int], preValues: Option[Int]): Option[Int] = {
    val currentCount = currentValues.sum//每个单词出现了多少次
    val preCount = preValues.getOrElse(0)

    Some(currentCount+preCount)//返回
  }
}
