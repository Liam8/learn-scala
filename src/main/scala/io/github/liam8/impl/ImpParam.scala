package io.github.liam8.impl

object ImpParam extends App {

  def bark(implicit name: String): Unit = println(s"$name say: Wang !")

  implicit val t: String = "Hot Dog"

  bark

}
