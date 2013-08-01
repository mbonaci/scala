package scala2e.chapter10

import Element.elem

object App extends App {
  val uniform = elem('u', 5, 1)
  val array = elem(Array("array1"))
  val line = elem("line")

  println(uniform beside uniform above array above line)


}