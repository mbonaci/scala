package scala2e.chapter12

import scala2e.chapter12.intqueue.MyQueue

object Chapter12 extends App {
//  val rect = new Rectangle(new Point(10, 5), new Point(20, 10))
//
//  println(rect)
  
  val q = new MyQueue
  q.put(10)
  println(q.get())
}