package scala2e.chapter12.intqueue

// Doubles all integers that are put in the queue
trait Doubling extends IntQueue {
  abstract override def put(x: Int) { super.put(2 * x) }
}