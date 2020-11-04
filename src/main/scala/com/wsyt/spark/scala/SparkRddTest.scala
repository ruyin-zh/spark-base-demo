package com.wsyt.spark.scala

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkRddTest {

  /**
   *
   * 面向数据集操作
   * 带函数的非聚合: map,flatmap
   * 1、单元素: union,cartesion 无函数计算
   * 2、kv元素: cogroup,join 无函数计算
   * 3、排序:
   * 4、聚合计算: reduceByKey,combinerByKey 有函数计算
   *
   * */

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setAppName("RDD_with").setMaster("local")
    val sc: SparkContext = new SparkContext(conf)

    /** 1、distinct内部实现
    val dataRDD : RDD[Int] = sc.parallelize(List(1, 2, 3, 4, 5, 6, 5, 4, 3, 2))

    val fieldRDD : RDD[Int] = dataRDD.filter(_ > 3)
    val arrCount: Array[Int] = fieldRDD.collect()
    arrCount.foreach(println)


    val distinctCount: RDD[Int] = fieldRDD.map((_, 1)).reduceByKey(_ + _).map(_._1)
    distinctCount.foreach(println)
    //distinct实际内部实现:  map(x => (x, null)).reduceByKey((x, _) => x, numPartitions).map(_._1)
    val distinctResult: RDD[Int] = dataRDD.distinct()
    distinctResult.foreach(println)
    */

    val rdd1: RDD[Int] = sc.parallelize(List(1, 2, 3, 4, 5))
    val rdd2: RDD[Int] = sc.parallelize(List(3, 4, 5, 6, 7))
    /** union内部实现
    val unionRdd: RDD[Int] = rdd1.union(rdd2)
    unionRdd.foreach(println)
     */

    /** cartesian内部实现
     * 笛卡尔积
     * spark很人性,面向数据集提供了不同的方法封装,且方法经过验证,无需人为干预;
     *
    val cartesianRdd: RDD[(Int, Int)] = rdd1.cartesian(rdd2)
    cartesianRdd.foreach(println)
    */

    //如果数据不需要区分每一条记录归属于哪个分区,间接地这类数据不需要对应的partitioner,故而也不需要shuffle操作;
    //shuffle的语意: 针对每一条记录计算它的分区号;
    //如果不需要区分记录,即不需要执行partitioner,那么本地直接通过IO进行数据拉取,
    // 这种方式相较于先执行partitioner,再进行计算,最终通过shuffle写入文件的过程要快;
    val subtractRdd: RDD[Int] = rdd1.subtract(rdd2)


    val kv1: RDD[(String, Int)] = sc.parallelize(List(("zhangsan", 11), ("zhangsan", 12), ("lisi", 13), ("wangwu", 14)))
    val kv2: RDD[(String, Int)] = sc.parallelize(List(("zhangsan", 21), ("zhangsan", 22), ("lisi", 23), ("zhaoliu", 28)))

    val cogroup: RDD[(String, (Iterable[Int], Iterable[Int]))] = kv1.cogroup(kv2)
    cogroup.foreach(println)

    val join: RDD[(String, (Int, Int))] = kv1.join(kv2)
    join.foreach(println)


    val left: RDD[(String, (Int, Option[Int]))] = kv1.leftOuterJoin(kv2)
    left.foreach(println)

    while (true){}
  }
}
