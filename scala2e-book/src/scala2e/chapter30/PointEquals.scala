package scala2e.chapter30

object PointEquals {
  def main(args: Array[String]): Unit = {
    
  }
}

class Point(var x: Int, var y: Int) {  // notice 'vars'
  override def hashCode = 41 * (41 + x) + y
  override def equals(other: Any) = other match {
    case that: Point => this.x == that.x && this.y == that.y
    case _ => false
  }
}

object Color extends Enumeration {
  val Red, Orange, Yellow, Green, Blue, Indigo, Violet = Value
}

class ColoredPoint(x: Int, y: Int, val color: Color.Value) extends Point(x, y) {
  override def equals(other: Any) = other match {
    case that: ColoredPoint =>
      (this.color == that.color) && super.equals(that)
    case that: Point =>  // special comparison for points
      that equals this
    case _ =>
      false
  }
}

