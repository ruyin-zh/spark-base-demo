package com.wsyt.spark.scala.topn

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

/**
 *
 * @Author ruyin_zh
 * @Date 2020/11/3
 * @Title TopN 分组排序--取同月份的中温度最高的两天
 * @Desc
 *
 * */
object SparkTopNCase1 {

  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf()
    conf.setMaster("local").setAppName("TopN1")
    val sc: SparkContext = new SparkContext(conf)
    sc.setLogLevel("ERROR")


    val file: RDD[String] = sc.textFile("data/climate.txt")

    val data: RDD[(Int, Int, Int, Int)] = file.map((line: String) => line.split(" "))
                                              .map((arr: Array[String]) => {
                                                val subArr: Array[String] = arr(0).split("-")
                                                (subArr(0).toInt,subArr(1).toInt,subArr(2).toInt,arr(1).toInt)})

    val grouped: RDD[((Int, Int), Iterable[(Int, Int)])] = data.map((t4: (Int, Int, Int, Int)) => ((t4._1, t4._2), (t4._3, t4._4))).groupByKey()

    val res: RDD[((Int, Int), List[(Int, Int)])] = grouped.mapValues((arr: Iterable[(Int, Int)]) => {
      val map: mutable.HashMap[Int, Int] = new mutable.HashMap[Int, Int]()
      arr.foreach((x: (Int, Int)) => {
        if (map.getOrElse(x._1,0) < x._2) map.put(x._1, x._2)
      })

      map.toList.sorted(new Ordering[(Int, Int)] {
        override def compare(x: (Int, Int), y: (Int, Int)) = y._2.compareTo(x._2)
      })
    })

    res.foreach(println)

    while (true){}
  }

}
