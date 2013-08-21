package scala2e.chapter20

object AbstractRational {

  // instead of class with two class parameters
  trait RationalTrait {
    val numerArg: Int
    val denomArg: Int
  }
  
  def main(args: Array[String]): Unit = {
    // example implementation of two abstract vals
    // yields an instance of an anonymous class
    // which mixes in the trait and is defined by the body
//    new RationalTrait {
//      val numerArg = 1
//      val denomArg = 2
//    }
    
    val x = 2
    val fun = new ProblematicRationalTrait {
      val numerArg = 1 * x
      val denomArg = 2 * x
    }
  }
  
  trait ProblematicRationalTrait {
    val numerArg: Int
    val denomArg: Int
    require(denomArg != 0)  // throws "requirement failed" exception
    private val g = gcd(numerArg, denomArg)
    val numer = numerArg / g
    val denom = denomArg / g
    
    private def gcd(a: Int, b: Int): Int =
      if (b == 0) a
      else gcd(b, a % b)
    
    override def toString = numer + "/" + denom
  }
}