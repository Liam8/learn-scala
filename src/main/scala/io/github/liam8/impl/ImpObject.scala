package io.github.liam8.impl

object ImpObject extends App {

  //定义一个`排序器`接口，能够比较两个相同类型的值的大小
  trait Ordering[T] {
    //如果x<y返回-1，x>y返回1，x==y则返回0.
    def compare(x: T, y: T): Int
  }

  //实现一个Int类型的排序器
  implicit object IntOrdering extends Ordering[Int] {
    override def compare(x: Int, y: Int): Int = {
      if (x < y) -1
      else if (x == y) 0
      else 1
    }
  }

  //实现一个String类型的排序器
  implicit object StringOrdering extends Ordering[String] {
    override def compare(x: String, y: String): Int = x.compareTo(y)
  }

  //一个通用的max函数
  def max[T](x: T, y: T)(implicit ord: Ordering[T]): T = {
    if (ord.compare(x, y) >= 0) x else y
  }

  def min[T: Ordering](x: T, y: T): T = {
    val ord = implicitly[Ordering[T]]
    if (ord.compare(x, y) >= 0) y else x
  }

  println(max(1, 2))
  println(max("a", "b"))

  println(min("a", "c"))
}
