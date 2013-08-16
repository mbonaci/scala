package scala2e.chapter17

import scala.collection.mutable.ListBuffer

object wksChapter17 {
  val buf = new ListBuffer[Int]                   //> buf  : scala.collection.mutable.ListBuffer[Int] = ListBuffer()

  buf += 22                                       //> res0: scala2e.chapter17.wksChapter17.buf.type = ListBuffer(22)
  11 +=: buf                                      //> res1: scala2e.chapter17.wksChapter17.buf.type = ListBuffer(11, 22)
  buf.toList                                      //> res2: List[Int] = List(11, 22)
}