package io.github.liam8.impl

object ImpParamWithCurry extends App {

  def bark(name: String)(implicit word: String): Unit = println(s"$name say: $word !")

  implicit val w: String = "Wang"

  bark("Hot Dog")

}
