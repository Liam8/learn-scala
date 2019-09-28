package io.github.liam8.con

import java.util.concurrent.{ArrayBlockingQueue, Callable, Future, RejectedExecutionException, ThreadPoolExecutor, TimeUnit}

object ExecutorDemo {

  def main(args: Array[String]): Unit = {
    // corePoolSize=1,maximumPoolSize=2,queue capacity=1
    val executor = new ThreadPoolExecutor(
      1,
      2,
      10,
      TimeUnit.SECONDS,
      new ArrayBlockingQueue[Runnable](1)
    )
    val task1 = new Runnable {
      override def run(): Unit = {
        println("task1 running")
        Thread.sleep(3000)
        println("task1 complete")
      }
    }
    val task2 = new Runnable {
      override def run(): Unit = {
        println("task2 running")
        Thread.sleep(3000)
        println("task2 complete")
      }
    }
    val task3 = new Callable[String] {
      override def call(): String = {
        println("task3 running")
        Thread.sleep(3000)
        println("task3 complete")
        "xxx"
      }
    }
    val task4 = new Runnable {
      override def run(): Unit = {
        println("task4 running")
        Thread.sleep(3000)
        println("task4 complete")
      }
    }
    var task2Result: Future[String] = null
    var taskCount = 1
    try {
      executor.execute(task1)
      println("task1 submitted")
      taskCount += 1
      executor.execute(task2)
      println("task2 submitted")
      taskCount += 1
      task2Result = executor.submit(task3)
      println("task3 submitted")
      taskCount += 1
      executor.execute(task4)
      println("task4 submitted")
    } catch {
      case e: RejectedExecutionException => println(s"task $taskCount be rejected")
    }
    // 起一个线程跟踪线程池大小
    val th = new Thread {
      var threadNum = 0

      override def run(): Unit =
        while (true) {
          if (executor.getPoolSize != threadNum) {
            threadNum = executor.getPoolSize
            println("pool size:" + threadNum)
          }
          Thread.sleep(100)
        }
    }
    th.setDaemon(true)
    th.start()
    if (task2Result != null) {
      println(task2Result.get(7, TimeUnit.SECONDS))
    }
    Thread.sleep(5000)
    executor.shutdown()

  }


}
