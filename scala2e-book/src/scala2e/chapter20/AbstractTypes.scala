package scala2e.chapter20

object AbstractTypes {

  def main(args: Array[String]): Unit = {
    val out1 = new Outer
    val out2 = new Outer
    
    val in1 = new out1.Inner
    
  }

}

class Outer {
  class Inner
}