package scala2e.chapter4

import scala2e.chapter04.ChecksumAccumulator.calculate
import scala2e.chapter06.Rational


object Fall {

  calculate("s")                                  //> res0: Int = -115

  implicit def intToRational(i: Int) = new Rational(i)
                                                  //> intToRational: (i: Int)scala2e.chapter06.Rational
  println("""|Welcome to Ultamix 3000.
             |Type "HELP" for help.""".stripMargin)
                                                  //> Welcome to Ultamix 3000.
                                                  //| Type "HELP" for help.

  val res = 11 % 4                                //> res  : Int = 3
  val rez = 11.0 % 4                              //> rez  : Double = 3.0
  val ie754 = math.IEEEremainder(11.0, 4.0)       //> ie754  : Double = -1.0
  val not = !(1 > 2)                              //> not  : Boolean = true
  
  // chapter 6 - Functional Objects
  val aux = new Rational(223)                     //> aux  : scala2e.chapter06.Rational = 223

  val r = new Rational(1, 2)                      //> r  : scala2e.chapter06.Rational = 1/2
  val o = new Rational(2, 3)                      //> o  : scala2e.chapter06.Rational = 2/3
  
  val add = r + o                                 //> add  : scala2e.chapter06.Rational = 7/6
  val mul = r * o                                 //> mul  : scala2e.chapter06.Rational = 1/3
  val dev = r / o                                 //> dev  : scala2e.chapter06.Rational = 3/4
  val sub = r - o                                 //> sub  : scala2e.chapter06.Rational = -1/6

  val impl = 2 * r                                //> impl  : scala2e.chapter06.Rational = 1

  val oneHalf = new Rational(1, 2)                //> oneHalf  : scala2e.chapter06.Rational = 1/2
  val twoThirds = new Rational(2, 3)              //> twoThirds  : scala2e.chapter06.Rational = 2/3
  val result = (oneHalf / 7) + (1 - twoThirds)    //> result  : scala2e.chapter06.Rational = 17/42
  
  val n = 8                                       //> n  : Int = 8
  val half =
    if (n % 2 == 0)
      n / 2
    else
      throw new RuntimeException("n must be even")//> half  : Int = 4
      
  // chapter 8 - Functions and Closures
  val randomNums = List(2, 5, -2, -43, 72)        //> randomNums  : List[Int] = List(2, 5, -2, -43, 72)
  randomNums.foreach((x: Int) => println(x))      //> 2
                                                  //| 5
                                                  //| -2
                                                  //| -43
                                                  //| 72
  
  val rn = randomNums.filter((x: Int) => x % 2 == 0)
                                                  //> rn  : List[Int] = List(2, -2, 72)
  rn.foreach((x: Int) => println(x))              //> 2
                                                  //| -2
                                                  //| 72
  
  
  val f = (_: Int) + (_: Int)                     //> f  : (Int, Int) => Int = <function2>
  f(5, 10)                                        //> res1: Int = 15
  
  rn.foreach(println)                             //> 2
                                                  //| -2
                                                  //| 72
                                                  
val someNumbers = List(-11, -10, -5, 0, 5, 10)    //> someNumbers  : List[Int] = List(-11, -10, -5, 0, 5, 10)
var sum = 0                                       //> sum  : Int = 0
someNumbers.foreach(sum += _)
sum                                               //> res2: Int = -11









}