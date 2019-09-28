package io.github.liam8.con

import java.util.concurrent.Executors

import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutorService, Future}
import scala.concurrent.duration._

object ExecutionContextDemo {

  def main(args: Array[String]): Unit = {
    val pool = Executors.newFixedThreadPool(2)
    implicit val ec: ExecutionContextExecutorService = ExecutionContext.fromExecutorService(pool)

    val f = Future {
      val t = Thread.currentThread().getName
      println(s"$t: future is coming")
      123
    }

    val re = f.map(r => {
      val t = Thread.currentThread().getName
      println(s"$t: mapping")
      r * r
    })
    re.onSuccess { case x: Int => println(x) }

    Await.result(f, 3.seconds)
    ec.shutdown()
  }

}