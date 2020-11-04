package com.wsyt.base.scala

import java.util

object BaseImplicitTest {


  def main(args: Array[String]): Unit = {
    val linked = new util.LinkedList[Int]()

    linked.add(1)
    linked.add(2)
    linked.add(3)

    val array = new util.ArrayList[Int]()

    array.add(5)
    array.add(6)
    array.add(7)

//    linked.forEach(println)



//    def foreach[T](list:util.LinkedList[T],f:(T) => Unit): Unit ={
//      val iter = list.iterator()
//      while (iter.hasNext) f(iter.next())
//    }

//    foreach(linked,println)


//    val xx = new XXX(linked)
//    xx.foreach(println)

    implicit def testTransferLinked[T](ls:util.LinkedList[T]) = {

      //      new ExtendIteratorWithLinked(ls)
      val iter: util.Iterator[T] = ls.iterator()
      new ExIteratorTransfer(iter)
    }

    implicit def testTransferArray[T](ls:util.ArrayList[T])={
      val iter = ls.iterator()
      new ExIteratorTransfer(iter)
    }

//    implicit def testTransfer[T](iter:Iterator[T])={
//      new ExIteratorTransfer(iter)
//    }
//
    linked.foreach(println)
    array.forEach(println)
//
//    val iter = linked.iterator()
//
//    iter.foreach(println)


    class ImplicitClassTest{}



    implicit val username: String = "Mike"
    //将报错,编译器无法准确知道指定的隐式参数
    //implicit val name = "Bob"
    implicit val tt = 12
    //currying
    def getInfo(age:Int)(implicit name:String) ={
      println(s"$name\t$age")
    }

    getInfo(age = 22)



  }



}

class ExtendIteratorWithLinked[T](list:util.LinkedList[T]){

  def foreach(f:(T) => Unit)={
    val iter = list.iterator()
    while (iter.hasNext) f(iter.next())
  }
}

class ExtendIteratorWithArray[T](list:util.ArrayList[T]){

  def foreach(f:(T) => Unit)={
    val iter = list.iterator()
    while (iter.hasNext) f(iter.next())
  }
}

class ExIteratorTransfer[T](iter:util.Iterator[T]){

  def foreach(f:(T) => Unit) = {
    while (iter.hasNext) f(iter.next())
  }
}





