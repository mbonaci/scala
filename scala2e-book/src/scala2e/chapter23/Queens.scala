package scala2e.chapter23

object Queens {

  def main(args: Array[String]): Unit = {
    printSolutions(queens(8))
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
  
    
def printSolutions(tbls: List[List[(Int, Int)]]) = {
  def printSolution(tbl: List[(Int, Int)]) = {
    val len = tbl.head._1  // since it's upside down
    println("_" * (len * 2))
    for {
      dim <- tbl.reverse   // all dimensions
      col <- 1 to len      // we put something in every cell
      pipe = if (col == 1) "|" else ""
      cell = if (col == dim._2) "Q|" else "_|"
      nl = if (col == len) "\n" else ""
    } print(pipe + cell + nl)
  }
  for (t <- tbls) printSolution(t)
}
	  
}