package scala2e.chapter4

object Ch9 {
  
  var assertionsEnabled = true                    //> assertionsEnabled  : Boolean = true
  
  def myAssert(predicate: () => Boolean) =
    if (assertionsEnabled && !predicate())
      throw new AssertionError                    //> myAssert: (predicate: () => Boolean)Unit

  val a = myAssert(() => 5 > 3)                   //> a  : Unit = ()
  
  def byNameAssert(predicate: => Boolean) =
    if (assertionsEnabled && !predicate)
      throw new AssertionError                    //> byNameAssert: (predicate: => Boolean)Unit
  
  val b = byNameAssert(5 > 3)                     //> b  : Unit = ()
}