package scala2e.chapter15

abstract class Expr
case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

object Chapter15 extends App {
  
  def simplifyBad(expr: Expr): Expr = expr match {
	  case UnOp(op, e) => UnOp(op, simplifyBad(e))
	  case UnOp("-", UnOp("-", e)) => e
  }     
}