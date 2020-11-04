package com.wsyt.spark.scala.topn

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @Author ruyin_zh
 * @Date 2020/11/3
 * @Title
 * @Desc
 *
 * */
object SparkTopNCase2 {


  implicit val ordered = new Ordering[(Int,Int)]{
    override def compare(x: (Int, Int), y: (Int, Int)) = y._2.compareTo(x._2)
  }

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("TopN2").setMaster("local")
    val sc = new SparkContext(conf)
    sc.setLogLevel("ERROR")

    val file: RDD[String] = sc.textFile("data/climate.txt")

    val data: RDD[(Int, Int, Int, Int)] = file.map(line => line.split(" ")).map(arr => {
      val subArr: Array[String] = arr(0).split("-")
      (subArr(0).toInt, subArr(1).toInt, subArr(2).toInt, arr(1).toInt)
    })

    val reduced: RDD[((Int, Int, Int), Int)] = data.map(t4 => ((t4._1, t4._2, t4._3), t4._4)).reduceByKey((x: Int, y: Int) => if (x < y) y else x)
    val mapped: RDD[((Int, Int), (Int, Int))] = reduced.map(t2 => ((t2._1._1, t2._1._2), (t2._1._3, t2._2)))
    val grouped: RDD[((Int, Int), Iterable[(Int, Int)])] = mapped.groupByKey()
    ///此处sorted会调用隐式函数
    grouped.mapValues(arr => arr.toList.sorted.take(2)).foreach(println)

    while (true){}
  }
}
