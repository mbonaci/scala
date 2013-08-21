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
    new RationalTrait {
      val numerArg = 1
      val denomArg = 2
    }
    
    val x = 2
    val fun = new {
      val numerArg = 1 * x
      val denomArg = 2 * x
    } with ProblematicRationalTrait 
    
    val rat = new {
      val numerArg = x
      val denomArg = x * 2  // error: value numerArg is not a member of object
    } with ProblematicRationalTrait
    
    new LazyRationalTrait {
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
  
  trait LazyRationalTrait {
    val numerArg: Int
    val denomArg: Int
    lazy val numer = numerArg / g
    lazy val denom = denomArg / g
    override def toString = numer + "/" + denom
    
    private lazy val g = {
      require(denomArg != 0)
      gcd(numerArg, denomArg)
    }
    
    private def gcd(a: Int, b: Int): Int =
      if (b == 0) a else gcd(b, a % b)
  }
}