package com.spark

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object StatefulWordCount {

   def main(args: Array[String]): Unit = {
     val sparkConf = new SparkConf().setAppName("StatefulWordCount").setMaster("local[2]")

     val ssc = new StreamingContext(sparkConf,Seconds(5))

    //使用 stateful算子必须设置checkPoint
    //在生产环境中建议将checkPoint设置为hdfs文件系统中的一个文件
    ssc.checkpoint(".")

    //使用nc工具：nc -lk 12345
    val lines=ssc.socketTextStream("192.168.1.138",6789)

    val result =lines.flatMap(_.split(" ")).map((_,1))
     val state = result.updateStateByKey[Int](updateFunction _)


    state.print()

    //启动Spark Streaming
    ssc.start()

    ssc.awaitTermination()
  }

 val updateFunc = (values: Seq[Int], state: Option[Int]) => {
  val currentCount = values.sum
  val previousCount = state.getOrElse(0)
  Some(currentCount + previousCount)
 }


}
