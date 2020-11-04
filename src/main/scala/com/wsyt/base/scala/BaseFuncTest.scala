package com.wsyt.base.scala

import java.util
import java.util.Date

object BaseFuncTest extends App {

  println("-------------------函数定义/函数返回值/返回值类型推断------------------------")

  def func01() = {
    println("Hello world")
  }

  def func02() = {
    new util.LinkedList[String]()
  }

  def func03() = {
  }

  def func04(num: Int): Int = {
    if (num == 1) {
      num
    } else {
      num * func04(num - 1)
    }
  }

  private val i: Int = func04(5)
  println(i)

  println("-------------------默认值函数------------------------")

  def func05(a: Int = 8, b: String = "abc") = {
    println(s"$a\t$b")
  }

  func05()
  func05(22)
  func05(b = "bcd")

  println("------------------匿名函数---------------------")

  var y: (Int, String) => String = (a: Int, b: String) => {
    a + b
  }

  println(y(12, "ab"))


  println("------------------嵌套函数---------------------")

  def func06(a: String) = {

    def func05() = {
      println(a)
    }

    func05()
  }

  func06("Hello Scala")

  println("------------------偏应用函数---------------------")

  def func07(date: Date, tp: String, msg: String) = {
    println(s"$date\t$tp\t$msg")
  }

  func07(new Date(), "Error", "indexOutOfBoundOfArray")

  var error = func07(_: Date, "Error", _: String)
  error(new Date(), "divide by zero")

  println("------------------可变长度函数---------------------")

  def func08(a: Int*) = {
    //    for (ax <- a){
    //      println(ax)
    //    }

    //a.foreach((x:Int) => println(x))
    //a.foreach(println(_))
    a.foreach(println)
  }

  func08(1, 2, 3, 4, 5)


  println("------------------高阶函数---------------------")

  def compute(a: Int, b: Int, f: (Int, Int) => Int) = {
    var res = f(a, b);
    println(res)
  }

  compute(3, 8, (x, y) => {
    x + y
  })
  compute(4, 6, (x, y) => {
    x * y
  })
  compute(5, 6, _ * _)

  //函数作为返回值
  def factory(op: String): (Int, Int) => Int = {
    def plus(x: Int, y: Int): Int = {
      x + y
    }

    if (op.equals("+")) {
      plus
    } else if (op.equals("*")) {
      (a: Int, b: Int) => {
        a * b
      }
    } else {
      null
    }
  }

  compute(4, 6, factory("+"))
  compute(3, 8, factory("*"))
  //compute(5,6,factory("-"))


  println("------------------柯里化---------------------")

  def currying(a: Int*)(b: String*) = {
    a.foreach(println)
    b.foreach(println)
  }

  currying(1, 2, 3)("A", "B", "C")
}
