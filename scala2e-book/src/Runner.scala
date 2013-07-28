//import scala2e.chapter4.ChecksumAccumulator
//import scala2e.chapter6.Rational
import scala2e.chapter7.GeneratorsAndFilters

object Runner extends App {
  /*
  implicit def intToRational(i: Int) = new Rational(i)
  val r = new Rational(66, 42)
  println(r)
  */

  val gaf = new GeneratorsAndFilters
  val ar = gaf.grep(".*11.*")
  
//  val files = gaf.scalaFiles
//  println("Number of files: " + files.length)
  
  def searchFrom(i: Int): Int =
	if (i >= ar.length) -1
	else if (ar(i).startsWith("c")) searchFrom(i + 1)
	else if (ar(i).endsWith("3")) i
	else searchFrom(i + 1)
	
  val result = searchFrom(0)	  
  println(result)
  
}