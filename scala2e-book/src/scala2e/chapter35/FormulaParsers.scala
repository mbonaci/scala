package scala2e.chapter35

import scala.util.parsing.combinator.RegexParsers

object FormulaParsers extends RegexParsers {
  def ident: Parser[String] = """[a-zA-Z_]\w*""".r
  def decimal: Parser[String] = """-?\d+(\.\d*)?""".r
  
  def cell: Parser[Coord] = 
    """[A-Za-z]\d+""".r ^^ { s =>
      val column = s.charAt(0).toUpper - 'A'
      val row = s.substring(1).toInt
      Coord(row, column)
    }
  
  def range: Parser[Range] = 
    cell~":"~cell ^^ {
      case c1~":"~c2 => Range(c1, c2)
    }
  
  def number: Parser[Number] =
    decimal ^^ (d => Number(d.toDouble))
    
  def application: Parser[Application] =
    ident~"("~repsep(expr, ",")~")" ^^ {
      case f~"("~ps~")" => Application(f, ps)
    }
  
  def expr: Parser[Formula] =
    range | cell | number | application
    
  def textual: Parser[Textual] =
    """[^=].*""".r ^^ Textual
    
  def formula: Parser[Formula] =
    number | textual | "="~>expr
    
  def parse(input: String): Formula =
    parseAll(formula, input) match {
      case Success(e, _) => e
      case f: NoSuccess => Textual("[" + f.msg + "]")
    }
    
  
}