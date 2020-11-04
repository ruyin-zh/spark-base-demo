package com.wsyt.spark.scala

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object ScalaWordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    //应用名称
    conf.setAppName("ScalaWordCount")
    //本地运行
    conf.setMaster("local")

    val sc: SparkContext = new SparkContext(conf)

    val fileRDD: RDD[String] = sc.textFile("data/word_count.txt")

    //hello spark
    val words: RDD[String] = fileRDD.flatMap((x) => x.split(" "))

    //hello
    //spark
    val pairWord: RDD[(String, Int)] = words.map(x => new Tuple2(x, 1))

    //(hello,1)
    //(spark,1)
    val result: RDD[(String, Int)] = pairWord.reduceByKey((x: Int, y: Int) => x + y)

    val convert: RDD[(Int, Int)] = result.map(x => (x._2, 1))
    val count: RDD[(Int, Int)] = convert.reduceByKey(_ + _)
    //result.foreach(println)

    result.foreach(println)
    count.foreach(println)

    while (true){}
    //以上程序精简版本
    /**sc.textFile("data/word_count.txt").
      flatMap(_.split(" ")).
      map((_,1)).
      reduceByKey(_+_).
      foreach(println)*/
  }

}
