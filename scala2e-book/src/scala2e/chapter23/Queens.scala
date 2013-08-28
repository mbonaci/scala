package scala2e.chapter23

object Queens {

  def main(args: Array[String]): Unit = {
    printTables(queens(8))
  }
  
  def queens(n: Int): List[List[(Int, Int)]] = {
    def placeQueens(k: Int): List[List[(Int, Int)]] =
      if (k == 0)
        List(List())
      else
        for {
          queens <- placeQueens(k - 1)
          column <- 1 to n
          queen = (k, column)
          if isSafe(queen, queens)
        } yield queen :: queens
     
    placeQueens(n)
  }
  
  def isSafe(queen: (Int, Int), queens: List[(Int, Int)]) =
    queens forall (q => !inCheck(queen, q))
  
  def inCheck(q1: (Int, Int), q2: (Int, Int)) =
    q1._2 == q2._2 ||  // in the same column
    (q1._1 - q2._1).abs == (q1._2 - q2._2).abs  // in diagonal
  
    
  def printTables(tbls: List[List[(Int, Int)]]) = {
    def printTable(row: List[(Int, Int)]) = {
      val len = row.head._1
      println("_" * (len * 2))
      val tbl = 
        for {
          pos <- row.reverse
          col <- 1 to len
          pipe = if (col == 1) "|" else ""
          mark = if (col == pos._2) "Q|" else "_|"
          nl = if (col == len) "\n" else ""
        } print(pipe + mark + nl)
    }
    for (tbl <- tbls) printTable(tbl)
  }
	  
}