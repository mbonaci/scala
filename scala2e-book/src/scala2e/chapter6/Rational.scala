package scala2e.chapter6

class Rational(n: Int, d: Int){
  require(d != 0)
  private val g = gcd(n.abs, d.abs)
  val numer: Int = n / g
  val denom: Int = d / g
  
  def this(i: Int) = this(i, 1)
  def this(d: Double) = this(d.toInt)
  
  override def toString = {
    val den = if(denom != 1) "/" + denom else ""
    numer + den
  }
  
  private def gcd(a: Int, b: Int): Int = 
    if (b == 0) a else gcd(b, a % b)
  
  def +(that: Rational): Rational = {
    new Rational(
        numer * that.denom + denom * that.numer,
        denom * that.denom
    )
  }
  
  def *(that: Rational): Rational = {
    new Rational(
        numer * that.numer,
        denom * that.denom
    )
  }
  
  def /(that: Rational): Rational = {
    new Rational(
        numer * that.denom,
        denom * that.numer
    )
  }
  
  def -(that: Rational): Rational = {
    new Rational(
        numer * that.denom - denom * that.numer,
        denom * that.denom
    )
  }
  
  def +(that: Int): Rational = {
    new Rational(
        numer + denom * that,
        denom
    )
  }
  
  def *(that: Int): Rational = {
    new Rational(
        numer * that,
        denom
    )
  }
  
  def /(that: Int): Rational = {
    new Rational(
        numer,
        denom * that
    )
  }
  
  def -(that: Int): Rational = {
    new Rational(
        numer - denom * that,
        denom
    )
  }
  
  def lessThan(that: Rational): Boolean = {
    numer * that.denom < denom * that.numer
  }
  
  def max(that: Rational): Rational = {
    if(this.lessThan(that)) that else this
  }
}
