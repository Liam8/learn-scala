package io.github.liam8.scala.con

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.{Lock, ReentrantLock}

object LockDemo {

  private val rtl: Lock = new ReentrantLock()

  var inc: Int = 0

  def get(): Int = {
    rtl.lock()
    try {
      inc
    } finally {
      rtl.unlock()
    }
  }

  def addOne(): Unit = {
    rtl.lock()
    try {
      TimeUnit.SECONDS.sleep(1)
      inc = 1 + get()
    } finally {
      rtl.unlock()
    }
  }

  def main(args: Array[String]): Unit = {
    for (i <- 1 to 10) {
      new Thread {
          override def run(): Unit = {
            println(s"run thread $i")
            addOne()
          }
      }.start()
    }
    while (true) {
      println(s"inc=$inc")
      TimeUnit.SECONDS.sleep(1)
    }
  }
}
