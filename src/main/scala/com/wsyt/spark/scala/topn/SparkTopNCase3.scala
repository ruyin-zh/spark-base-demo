package com.wsyt.spark.scala.topn

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @Author ruyin_zh
 * @Date 2020/11/4
 * @Title
 * @Desc
 *
 * */
object SparkTopNCase3 {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("TopN3").setMaster("local")
    val sc = new SparkContext(conf)

    sc.setLogLevel("ERROR")

    val file: RDD[String] = sc.textFile("data/climate.txt")
    val data: RDD[(Int, Int, Int, Int)] = file.map(line => line.split(" ")).map(arr => {
      val subArr: Array[String] = arr(0).split("-")
      (subArr(0).toInt, subArr(1).toInt, subArr(2).toInt, arr(1).toInt)
    })

    val sorted: RDD[(Int, Int, Int, Int)] = data.sortBy((t4: (Int, Int, Int, Int)) => (t4._1, t4._2, t4._4), false)
    val reduced: RDD[((Int, Int, Int), Int)] = sorted.map(t4 => ((t4._1, t4._2, t4._3), t4._4)).reduceByKey((x: Int, y: Int) => if (x < y) y else x)
    val mapped: RDD[((Int, Int), (Int, Int))] = reduced.map(t2 => ((t2._1._1, t2._1._2), (t2._1._3, t2._2)))
    val grouped: RDD[((Int, Int), List[(Int, Int)])] = mapped.groupByKey().mapValues(it => it.toList)
    grouped.foreach(println)

    while (true){}
  }

}
