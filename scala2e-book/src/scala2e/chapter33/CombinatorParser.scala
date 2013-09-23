package scala2e.chapter33

import scala.util.parsing.combinator._

class Arith extends JavaTokenParsers {
  def expr: Parser[Any] = term~rep("+"~term | "-"~term)
  def term: Parser[Any] = factor~rep("*"~factor | "/"~factor)
  def factor: Parser[Any] = floatingPointNumber | "("~expr~")"
}
/*
object ParseExpr extends Arith {
  def main(args: Array[String]) {
    val arg = "2 * (3 + 7))"
    println("input: " + arg)
    println(parseAll(expr, arg))
  }
}
*/
import scala.util.parsing.combinator._
class JSON extends JavaTokenParsers {
  def value:   Parser[Any] = obj | arr | stringLiteral | 
                             floatingPointNumber | "null" | "true" | "false"
  def obj:     Parser[Any] = "{"~repsep(member, ",")~"}"
  def arr:     Parser[Any] = "["~repsep(value, ",")~"]"
  def member:  Parser[Any] = stringLiteral~":"~value
}

import java.io.FileReader
object ParseJSON extends JSON {
  def main(args: Array[String]) {
    val reader = new FileReader(args(0))
    // parseAll is overloaded: takes sequence or input reader as a second argument
    println(parseAll(value, reader))
  }
}
