package scala2e.chapter35
import swing._
import event._

class Model(val height: Int, val width: Int)
  extends Evaluator with Arithmetic {
  
  val cells = Array.ofDim[Cell](height, width)
  for (i <- 0 until height; j <- 0 until width)
    cells(i)(j) = new Cell(i, j)
  
  case class Cell(row: Int, column: Int) extends Publisher {
    private var v: Double = 0
    def value: Double = v
    def value_=(w: Double) {
      if (!(v == w || v.isNaN && w.isNaN)) {
        v = w
        publish(ValueChanged(this))
      }
    }
    
    private var f: Formula = Empty
    def formula: Formula = f
    def formula_=(f: Formula) {
      for (c <- references(formula)) deafTo(c)
      this.f = f
      for (c <- references(formula)) listenTo(c)
      value = evaluate(f)
    }
    
    override def toString = formula match {
      case Textual(x) => x
      case _ => value.toString
    }
    
    reactions += {
      case ValueChanged(_) => value = evaluate(formula)
    }
  }
  
  case class ValueChanged(cell: Cell) extends event.Event
}
