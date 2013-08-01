package scala2e.chapter10

import Element.elem

abstract class Element {
  def contents: Array[String]
  
  // The height method returns the number of lines in contents
  def height: Int = contents.length
  /*
   * The width method returns the length of the first line, or, if there
   * are no lines in the element, zero. 
   * This means you cannot define an element 
   * with a height of zero and a non-zero width.
   */ 
  def width: Int = if(height == 0) 0 else contents(0).length() 
  
  def above(that: Element): Element =
    elem(this.contents ++ that.contents)
  
  def besideImperative(that: Element): Element = {
    val contents = new Array[String](this.contents.length)
    for(i <- 0 until this.contents.length)
      contents(i) = this.contents(i) + that.contents(i)
    
    elem(contents)
  }
  
  def beside(that: Element): Element =
    elem(
      for(
        //defines a new Tuple2 for each iteration:
        (line1, line2) <- this.contents zip that.contents
      ) yield line1 + line2
    )
  
  override def toString = contents mkString "\n"
}

object Element {
  def elem(contents: Array[String]): Element =
    new ArrayElement(contents)
  def elem(s: String): Element =
    new LineElement(s)
  def elem(ch: Char, width: Int, height: Int): Element =
    new UniformElement(ch, width, height)
}