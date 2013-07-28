package scala2e.chapter4

import ChecksumAccumulator.calculate
import scala2e.chapter6.Rational


object Fall {

  implicit def intToRational(i: Int) = new Rational(i)
                                                  //> intToRational: (i: Int)scala2e.chapter6.Rational
  calculate("s")                                  //> res0: Int = -115

  println("""|Welcome to Ultamix 3000.
             |Type "HELP" for help.""".stripMargin)
                                                  //> Welcome to Ultamix 3000.
                                                  //| Type "HELP" for help.

  val res = 11 % 4                                //> res  : Int = 3
  val rez = 11.0 % 4                              //> rez  : Double = 3.0
  val ie754 = math.IEEEremainder(11.0, 4.0)       //> ie754  : Double = -1.0
  val not = !(1 > 2)                              //> not  : Boolean = true
  

  val aux = new Rational(223)                     //> aux  : scala2e.chapter6.Rational = 223

  val r = new Rational(1, 2)                      //> r  : scala2e.chapter6.Rational = 1/2
  val o = new Rational(2, 3)                      //> o  : scala2e.chapter6.Rational = 2/3
  
  val add = r + o                                 //> add  : scala2e.chapter6.Rational = 7/6
  val mul = r * o                                 //> mul  : scala2e.chapter6.Rational = 1/3
  val dev = r / o                                 //> dev  : scala2e.chapter6.Rational = 3/4
  val sub = r - o                                 //> sub  : scala2e.chapter6.Rational = -1/6

  val impl = 2 * r                                //> impl  : scala2e.chapter6.Rational = 1

  val oneHalf = new Rational(1, 2)                //> oneHalf  : scala2e.chapter6.Rational = 1/2
  val twoThirds = new Rational(2, 3)              //> twoThirds  : scala2e.chapter6.Rational = 2/3
  val result = (oneHalf / 7) + (1 - twoThirds)    //> result  : scala2e.chapter6.Rational = 17/42
  
  val n = 1                                       //> n  : Int = 1
  val half =
    if (n % 2 == 0)
      n / 2
    else
      throw new RuntimeException("n must be even")//> java.lang.RuntimeException: n must be even
                                                  //| 	at scala2e.chapter4.Fall$$anonfun$main$1.apply$mcV$sp(scala2e.chapter4.F
                                                  //| all.scala:42)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$$anonfun$$exe
                                                  //| cute$1.apply$mcV$sp(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:75)
                                                  //| 	at scala2e.chapter4.Fall$.main(scala2e.chapter4.Fall.scala:7)
                                                  //| 	at scala2e.chapter4.Fall.main(scala2e.chapter4.Fall.scala)
}