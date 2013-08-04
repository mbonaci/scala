package scala2e.chapter12.intqueue

trait Doubling extends IntQueue {
  abstract override def put(x: Int) { super.put(2 * x) }
}