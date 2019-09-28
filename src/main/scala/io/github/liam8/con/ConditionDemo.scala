package io.github.liam8.scala.con

import java.util.concurrent.locks.ReentrantLock


object ConditionDemo {
  def main(args: Array[String]): Unit = {
    val queue = new BoundedBuffer
    for (i <- 1 to 10) {
      new Thread {
        override def run(): Unit = {
          queue.put(s"Item$i")
        }
      }.start()
    }
    for (i <- 1 to 10) {
      new Thread {
        override def run(): Unit = {
          queue.take
        }
      }.start()
    }
  }

  class BoundedBuffer {
    final val lock = new ReentrantLock
    final val notFull = lock.newCondition
    final val notEmpty = lock.newCondition
    final val items = new Array[Any](3)
    var putptr = 0
    var takeptr = 0
    var count = 0

    def put(x: Any): Unit = {
      lock.lock()
      try {
        while (count == items.length) {
          notFull.await()
        }
        items(putptr) = x
        putptr += 1
        if (putptr == items.length) {
          putptr = 0
        }
        count += 1
        println(s"put $x")
        notEmpty.signal()
      } finally {
        lock.unlock()
      }
    }

    def take: Any = {
      lock.lock()
      try {
        while (count == 0) {
          notEmpty.await()
        }
        val x = items(takeptr)
        takeptr += 1
        if (takeptr == items.length) {
          takeptr = 0
        }
        count -= 1
        println(s"take $x")
        notFull.signal()
        x
      } finally {
        lock.unlock()
      }
    }
  }

}

/* from java doc
  class BoundedBuffer {
     final Lock lock = new ReentrantLock();
     final Condition notFull  = lock.newCondition();
     final Condition notEmpty = lock.newCondition();

     final Object[] items = new Object[100];
     int putptr, takeptr, count;

     public void put(Object x) throws InterruptedException {
       lock.lock();
       try {
         while (count == items.length)
           notFull.await();
         items[putptr] = x;
         if (++putptr == items.length) putptr = 0;
         ++count;
         notEmpty.signal();
       } finally {
         lock.unlock();
       }
     }

     public Object take() throws InterruptedException {
       lock.lock();
       try {
         while (count == 0)
           notEmpty.await();
         Object x = items[takeptr];
         if (++takeptr == items.length) takeptr = 0;
         --count;
         notFull.signal();
         return x;
       } finally {
         lock.unlock();
       }
     }
   }
 */