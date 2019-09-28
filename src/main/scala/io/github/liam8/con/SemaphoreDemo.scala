package io.github.liam8.scala.con

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.{ConcurrentLinkedDeque, Semaphore, TimeUnit}

import collection.JavaConversions._

object SemaphoreDemo {

  def main(args: Array[String]): Unit = {
    val pool = new SemaphoreDemo[String](2, List("aaaa", "bbbbb"))
    val formatDate = (date: Date) => new SimpleDateFormat("HH:mm:ss.SSS").format(date)
    for (i <- 1 to 10) {
      new Thread {
        override def run(): Unit = {
          pool.exec { e =>
            println(s"${formatDate(new Date)} thread $i using $e")
            TimeUnit.SECONDS.sleep(3)
            println(s"${formatDate(new Date)} thread $i done with $e")
          }
        }
      }.start()
    }
  }

}

class SemaphoreDemo[T](size: Int, items: List[T]) {

  val pool: ConcurrentLinkedDeque[T] = new ConcurrentLinkedDeque[T](items)

  val semaphore: Semaphore = new Semaphore(size)

  def exec(func: T => Unit): Unit = {
    semaphore.acquire()
    val t = pool.pop()
    try {
      func(t)
    } finally {
      pool.add(t)
      semaphore.release()
    }
  }
}