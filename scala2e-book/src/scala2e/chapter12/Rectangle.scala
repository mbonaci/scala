package scala2e.chapter12

class Rectangle(val topLeft: Point, val bottomRight: Point) extends Rectangular {
  val topIndent = "\n" * (top - 1)
  val leftIndent = " " * (left - 1)
  val bodyLine = " " * (width - 2)
    
  override def toString = {
    val horzEdge = leftIndent + ("_" * width) + "\n";
    val body = 
  	  for(i <- top to bottom)
  	  yield(leftIndent + "|" + bodyLine + "|")
      
    topIndent + horzEdge + (body mkString "\n") + "\n" + horzEdge
  }
}