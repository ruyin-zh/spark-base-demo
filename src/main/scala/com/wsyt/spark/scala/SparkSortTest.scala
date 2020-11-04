package com.wsyt.spark.scala

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 *
 * @Author ruyin_zh
 * @Date 2020/10/12
 * @Title
 * @Desc
 *
 * */
object SparkSortTest{

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("sort")
    val sc: SparkContext = new SparkContext(conf)
    sc.setLogLevel("ERROR")


    //pv,uv
    val file: RDD[String] = sc.textFile("data/access_statistic.txt", 3)

    val line: RDD[(String, Int)] = file.map(line => (line.split(" ")(3), 1))
    val count: RDD[(String, Int)] = line.reduceByKey(_ + _)
    val swap: RDD[(Int, String)] = count.map(_.swap)
    val sorted: RDD[(Int, String)] = swap.sortByKey(false)
    val result: RDD[(String, Int)] = sorted.map(_.swap)
    val arr: Array[(String, Int)] = result.take(5)
    arr.foreach(println)


    println("---------------------------------------------------------------------")

    val keys: RDD[(String, String)] = file.map(line => {
      val strs: Array[String] = line.split(" ")
      (strs(3), strs(0))
    })
    val map: RDD[(String, String)] = keys.distinct()
    val pair: RDD[(String, Int)] = map.map(k => (k._1, 1))
    val tt: RDD[(String, Int)] = pair.reduceByKey(_ + _)
    val mapSort: RDD[(String, Int)] = tt.sortBy(_._2, false)
    val res: Array[(String, Int)] = mapSort.take(5)
    res.foreach(println)

    while (true){}
  }
}
