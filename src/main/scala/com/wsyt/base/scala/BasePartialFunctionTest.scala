package com.wsyt.base.scala

object BasePartialFunctionTest {

  def main(args: Array[String]): Unit = {

    def basePartialFunc: PartialFunction[Any, String] = {
      case "hello" => "val is hello"
      case x: Int => s"$x is Int"
      case _ => "none"
    }

    println(basePartialFunc(44))
    println(basePartialFunc("hello"))
    println(basePartialFunc(false))
  }
}
