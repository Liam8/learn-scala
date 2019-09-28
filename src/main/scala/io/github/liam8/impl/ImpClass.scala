package io.github.liam8.impl

object ImpClass extends App {

  implicit class Dog(val name: String) {
    def bark(): Unit = println(s"$name say: Wang !")
  }

  "Teddy".bark()

}
