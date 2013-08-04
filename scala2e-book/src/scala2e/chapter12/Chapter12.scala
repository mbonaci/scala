package scala2e.chapter12

import scala2e.chapter12.intqueue.MyQueue
import scala2e.chapter12.intqueue.Filtering
import scala2e.chapter12.intqueue.Incrementing

object Chapter12 extends App {
//  val rect = new Rectangle(new Point(10, 5), new Point(20, 10))
//
//  println(rect)
  
  val q = new MyQueue with Incrementing with Filtering
  q.put(-1);
  q.put(0);
  q.put(1);
  println(q.get);
  println(q.get);
//  println(q.get);
}