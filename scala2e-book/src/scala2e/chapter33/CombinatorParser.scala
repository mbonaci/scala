package scala2e.chapter33

import scala.util.parsing.combinator._

class Arith extends JavaTokenParsers {
  def expr: Parser[Any] = term~rep("+"~term | "-"~term)
  def term: Parser[Any] = factor~rep("*"~factor | "/"~factor)
  def factor: Parser[Any] = floatingPointNumber | "("~expr~")"
}

object ParseExpr extends Arith {
  def main(args: Array[String]) {
    val arg = "2 * (3 + 7))"
    println("input: " + arg)
    println(parseAll(expr, arg))
  }
}
