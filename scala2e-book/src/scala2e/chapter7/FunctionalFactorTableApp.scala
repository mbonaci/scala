package scala2e.chapter7

object FunctionalFactorTableApp extends App {
  
  // Returns a row as a sequence
  def makeRowSeq(row: Int) =
    for(col <- 1 to 10) yield {
      val prod = (row * col).toString
      val space = " " * (4 - prod.length)
      space + prod
    }
  
  // Returns a row as a string
  def makeRow(row: Int) = makeRowSeq(row).mkString + "\n"
  
  // Returns table as a string with one row per line
  def multiTable() = {
    val table = for(row <- 1 to 10) yield makeRow(row)
    table.mkString
  } 
  
  print(multiTable)
}