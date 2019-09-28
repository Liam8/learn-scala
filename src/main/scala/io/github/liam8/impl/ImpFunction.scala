package io.github.liam8.impl

object ImpFunction extends App {

  class Dog(val name: String) {
    def bark(): Unit = println(s"$name say: Wang !")
  }

  implicit def double2int(d: Double): Int = d.toInt

  implicit def string2Dog(s: String): Dog = new Dog(s)

  val f: Int = 1.1 //转换为期望类型,1.1通过double2int转成了Int类型

  println(f)

  "Teddy".bark() // 转换方法的调用者,字符串通过string2Dog转成了Dog, 于是有了bark方法

}
