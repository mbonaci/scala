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
  
  def above(that: Element): Element = {
    val this1 = this widen that.width
    val that1 = that widen this.width
    //assert(this.width == that.width)
    elem(this1.contents ++ that1.contents)
  }
  
  def besideImperative(that: Element): Element = {
    val contents = new Array[String](this.contents.length)
    for(i <- 0 until this.contents.length)
      contents(i) = this.contents(i) + that.contents(i)
    
    elem(contents)
  }
  
  def beside(that: Element): Element = {
    val this1 = this heighten that.height
    val that1 = that heighten this.height
    elem(
      for(
        //defines a new Tuple2 for each iteration:
        (line1, line2) <- this1.contents zip that1.contents
      ) yield line1 + line2
    )
  }
    
  def heighten(h: Int): Element =
    if(h <= height) this
    else {
      val top = elem(' ', width, (h - height) / 2)
      val bot = elem(' ', width, h - height - top.height)
      top above this above bot
    }
  
  def widen(w: Int): Element =
    if(w <= width) this
    else {
      val left = elem(' ', (w - width) / 2, height)
      val right = elem(' ', w - width - left.width, height)
      left beside this beside right
    } ensuring(w <= _.width)
  
  override def toString = contents mkString "\n"

}

object Element {
  
  private class ArrayElement(
    val contents: Array[String]
  ) extends Element
  
  private class LineElement(s: String) extends Element {
    val contents = Array(s)
    override def width = s.length
    override def height = 1
  }
  
  private class UniformElement(
    ch: Char,
    override val width: Int,
    override val height: Int
  ) extends Element {
    private val line = ch.toString * width
    def contents = Array.fill(height)(line)
  }
  
  def elem(contents: Array[String]): Element =
    new ArrayElement(contents)
  
  def elem(s: String): Element =
    new LineElement(s)
  
  def elem(ch: Char, width: Int, height: Int): Element = {
    require(width > 0)
    new UniformElement(ch, width, height)
  }
}