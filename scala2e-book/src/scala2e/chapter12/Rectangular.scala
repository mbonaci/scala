package scala2e.chapter12

trait Rectangular {
  def topLeft: Point
  def bottomRight: Point
  
  def left = topLeft.x
  def right = bottomRight.x
  def top = topLeft.y
  def bottom = bottomRight.y
  def width = right - left
  def height = bottom - top

}