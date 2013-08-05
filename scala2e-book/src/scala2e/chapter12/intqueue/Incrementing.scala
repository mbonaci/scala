package scala2e.chapter12.intqueue

// Increments all integers placed in the queue
trait Incrementing extends IntQueue {
  abstract override def put(x: Int) = { super.put(x + 1) }
}