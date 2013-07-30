package scala2e.chapter4

import ChecksumAccumulator.calculate
import scala2e.chapter6.Rational


object Fall {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(167); 

  implicit def intToRational(i: Int) = new Rational(i);System.out.println("""intToRational: (i: Int)scala2e.chapter6.Rational""");$skip(17); val res$0 = 
  calculate("s");System.out.println("""res0: Int = """ + $show(res$0));$skip(92); 

  println("""|Welcome to Ultamix 3000.
             |Type "HELP" for help.""".stripMargin);$skip(20); 

  val res = 11 % 4;System.out.println("""res  : Int = """ + $show(res ));$skip(21); 
  val rez = 11.0 % 4;System.out.println("""rez  : Double = """ + $show(rez ));$skip(44); 
  val ie754 = math.IEEEremainder(11.0, 4.0);System.out.println("""ie754  : Double = """ + $show(ie754 ));$skip(21); 
  val not = !(1 > 2);System.out.println("""not  : Boolean = """ + $show(not ));$skip(70); 
  
  // chapter 6 - Functional Objects
  val aux = new Rational(223);System.out.println("""aux  : scala2e.chapter6.Rational = """ + $show(aux ));$skip(30); 

  val r = new Rational(1, 2);System.out.println("""r  : scala2e.chapter6.Rational = """ + $show(r ));$skip(29); 
  val o = new Rational(2, 3);System.out.println("""o  : scala2e.chapter6.Rational = """ + $show(o ));$skip(21); 
  
  val add = r + o;System.out.println("""add  : scala2e.chapter6.Rational = """ + $show(add ));$skip(18); 
  val mul = r * o;System.out.println("""mul  : scala2e.chapter6.Rational = """ + $show(mul ));$skip(18); 
  val dev = r / o;System.out.println("""dev  : scala2e.chapter6.Rational = """ + $show(dev ));$skip(18); 
  val sub = r - o;System.out.println("""sub  : scala2e.chapter6.Rational = """ + $show(sub ));$skip(20); 

  val impl = 2 * r;System.out.println("""impl  : scala2e.chapter6.Rational = """ + $show(impl ));$skip(36); 

  val oneHalf = new Rational(1, 2);System.out.println("""oneHalf  : scala2e.chapter6.Rational = """ + $show(oneHalf ));$skip(37); 
  val twoThirds = new Rational(2, 3);System.out.println("""twoThirds  : scala2e.chapter6.Rational = """ + $show(twoThirds ));$skip(47); 
  val result = (oneHalf / 7) + (1 - twoThirds);System.out.println("""result  : scala2e.chapter6.Rational = """ + $show(result ));$skip(15); 
  
  val n = 8;System.out.println("""n  : Int = """ + $show(n ));$skip(105); 
  val half =
    if (n % 2 == 0)
      n / 2
    else
      throw new RuntimeException("n must be even");System.out.println("""half  : Int = """ + $show(half ));$skip(90); 
      
  // chapter 8 - Functions and Closures
  val randomNums = List(2, 5, -2, -43, 72);System.out.println("""randomNums  : List[Int] = """ + $show(randomNums ));$skip(45); 
  randomNums.foreach((x: Int) => println(x));$skip(56); 
  
  val rn = randomNums.filter((x: Int) => x % 2 == 0);System.out.println("""rn  : List[Int] = """ + $show(rn ));$skip(37); 
  rn.foreach((x: Int) => println(x));$skip(36); 
  
  
  val f = (_: Int) + (_: Int);System.out.println("""f  : (Int, Int) => Int = """ + $show(f ));$skip(11); val res$1 = 
  f(5, 10);System.out.println("""res1: Int = """ + $show(res$1));$skip(25); 
  
  rn.foreach(println);$skip(98); 
                                                  
val someNumbers = List(-11, -10, -5, 0, 5, 10);System.out.println("""someNumbers  : List[Int] = """ + $show(someNumbers ));$skip(12); 
var sum = 0;System.out.println("""sum  : Int = """ + $show(sum ));$skip(30); 
someNumbers.foreach(sum += _);$skip(4); val res$2 = 
sum;System.out.println("""res2: Int = """ + $show(res$2))}









}
