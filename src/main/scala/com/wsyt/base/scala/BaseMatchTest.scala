package com.wsyt.base.scala

object BaseMatchTest {

  def main(args: Array[String]): Unit = {
    val tuple = (1.0, 88, "abc", true, 'a', 44)

    val iterator = tuple.productIterator

    val value = iterator.map((x) => {
      x match {
        case y: Int => println(s"$y....$x... is Int")
        case 1.0 => println(s"$x ... is 1.0")
        case false => println(s"$x ... is false")
        case w: Int if w > 50 => println(s"$w .. is >50")
        case _ => println(x)
      }
    })

    while (value.hasNext) {
      //println(value.next())
      value.next()
    }
  }

}
