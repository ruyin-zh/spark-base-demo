package com.wsyt.base.scala

import java.util

import scala.collection.mutable.ListBuffer

object BaseCollection {

  def main(args: Array[String]): Unit = {
    val linkList = new util.LinkedList[String]()
    linkList.add("scala")
    println(linkList)

    println("---------------------------array---------------------------")
    //scala拥有自己的collections
    //1、数组
    val arr = Array(1, 2)
    arr(1) = 99
    println(arr(1))

    for (elem <- arr) {
      println(elem)
    }

    arr.foreach(x => println(x))
    arr.foreach(println(_))
    arr.foreach(println)

    println("---------------------------list---------------------------")
    //2、链表
    //scala中collections中有两个包:immutable,mutable,默认的是不可变的immutable
    val list01 = List[Int](1, 2, 3)
    list01.foreach(println)
    val listEnhance = list01.map(x => x + 20)
    listEnhance.foreach(println)

    val listEnhance01 = list01.map(_ * 2)
    listEnhance01.foreach(println)

    val list02 = new ListBuffer[Int]
    list02.+=(33)
    list02.+=(34)
    list02.+=(35)

    //todo scala数据集中的 ++ += ++: :++
    list02.foreach(println)


    println("---------------------------set---------------------------")
    val set01 = Set(1, 2, 3, 3, 2, 1)
    set01.foreach(println)

    import scala.collection.mutable.Set
    val set02 = Set(11, 22, 33, 44)
    set02.add(55)
    set02.foreach(println)

    val set03 = scala.collection.immutable.Set(5, 6, 7)
    //set03.add


    println("---------------------------tuple---------------------------")
    val t2 = Tuple2(11, "ab")
    val t3 = Tuple3(22, "bcd", true)
    val t5 = (1, 2, 3, 4, 5)
    val t4 = (false, 22, 45, (x: Int) => {
      x + 3
    })
    val t4_1 = (false, 22, 45, (x: Int, y: Int) => {
      x + y + 2
    })

    println(t2._1)
    println(t5._3)

    println(t4._4)
    println(t4._4(5))
    println(t4_1._4)

    val iterator = t4_1.productIterator
    while (iterator.hasNext) {
      println(iterator.next())
    }


    println("---------------------------map---------------------------")
    val map01 = Map(("a", 11), "b" -> 22, ("c", 33))
    map01.foreach(x => println(s"${x._1} ${x._2}"))

    println(map01.getOrElse("w", -1))

    val map02 = scala.collection.mutable.Map(("a", 11), "b" -> 22)
    map02.put("d", 90)
    map02.foreach(x => println(s"${x._1} ${x._2}"))


    println("---------------------------进阶---------------------------")
    //无论是List还是Array均有相同的操作
    val listStr = List("hello scala", "hello jvm", "hello kernel")
    //val listStr = Array("hello scala", "hello jvm", "hello kernel")
    //val listStr = Set("hello scala", "hello jvm", "hello kernel")
    val flatMap = listStr.flatMap(_.split(" "))
    flatMap.foreach(println)

    val mapList = flatMap.map((_, 1))
    mapList.foreach(println)


    println("---------------------------再进阶(使用迭代器)---------------------------")
    val iter = listStr.iterator
    val iterFlatMap = iter.flatMap(_.split(" "))
    //迭代器指针指到数据流末尾再遍历讲不会再获取到数据
    //iterFlatMap.foreach(println)

    val iterMapList = iterFlatMap.map((_, 1))
    iterMapList.foreach(println)

    //map操作只对数据做映射
    //flatMap操作可以对数据进行扁平化处理
  }
}
