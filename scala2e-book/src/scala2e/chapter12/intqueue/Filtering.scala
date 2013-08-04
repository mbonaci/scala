package scala2e.chapter12.intqueue

// filters out negative integers from a queue
trait Filtering extends IntQueue {
  abstract override def put(x: Int) = { 
    if(x >= 0) super.put(x)
  }
}