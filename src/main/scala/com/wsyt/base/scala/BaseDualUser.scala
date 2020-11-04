package com.wsyt.base.scala

/**
 *
 *
 * 伴生对象
 *
 * */
object BaseDualUser {

  private var user = new BaseDualUser(24)
  private var user1 = new BaseDualUser("uzi")
  println("object init start")

  //private val name = "scala init"
  private val name = "scala init"

  def main(args: Array[String]): Unit = {
    println("object main method invoke")

    user.printMessage
    user1.printMessage
  }

  println("object init end")
}

class BaseDualUser(age:Int){

  def this(name:String){
    this(12)
    this.name = name
  }

  private var name = "u1"

  println("class User1 init 1")

  def printMessage:Unit = {
    println(s"class User1 invoke printMessage $name $age")

    //类似于静态字段引用
    println(BaseDualUser.name)
  }

  println("class User init 2")
}