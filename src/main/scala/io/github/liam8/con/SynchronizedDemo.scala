package io.github.liam8.scala.con

import java.util.concurrent.TimeUnit

object SynchronizedDemo {

  private var inc: Int = 0

  def addOne(): Unit = this.synchronized {
    TimeUnit.SECONDS.sleep(1)
    inc += 1
  }

  def main(args: Array[String]): Unit = {
    for (i <- 1 to 10) {
      new Thread {
        override def run(): Unit = {
          println(s"run thread with object method $i")
          addOne()
        }
      }.start()
    }
    val instance = new SynchronizedDemo
    for (i <- 1 to 10) {
      new Thread {
        override def run(): Unit = {
          println(s"run thread with class method $i")
          instance.addOne()
        }
      }.start()
    }
    while (true) {
      println(s"object inc=$inc, class inc=${instance.inc}")
      TimeUnit.SECONDS.sleep(1)
    }
  }


}

class SynchronizedDemo {
  private var inc: Int = 0

  def addOne(): Unit = SynchronizedDemo.synchronized {
    TimeUnit.SECONDS.sleep(1)
    inc += 1
  }
}