package io.github.liam8.impl

object ImpCompObject extends App {

  object Dog {
    implicit def dogToCat(d: Dog) = new Cat(d.name)
  }

  class Cat(val name: String) {
    def miao(): Unit = println(s"$name say: Miao !")
  }

  class Dog(val name: String) {
    def bark(): Unit = println(s"$name say: Wang !")
  }

  new Dog("Teddy").miao()

}
