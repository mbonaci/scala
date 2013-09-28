package scala2e.chapter35

import swing._, event._

class Spreadsheet(val height: Int, val width: Int) extends ScrollPane {
  val cellModel = new Model(height, width)
  import cellModel._
  
  val table = new Table(height, width) {
    rowHeight = 20
    autoResizeMode = Table.AutoResizeMode.Off
    showGrid = true
    gridColor = new java.awt.Color(150, 150, 150)
    
    override def rendererComponent(isSelected: Boolean, 
        hasFocus: Boolean, row: Int, column: Int) =
      if (hasFocus) new TextField(userData(row, column))
      else
        new Label(cells(row)(column).toString) {
          xAlignment = Alignment.Right
      }
    
    def userData(row: Int, column: Int): String = {
      val v = this(row, column)
      if (v == null) " " else v.toString
    }
    
    reactions += {
      case TableUpdated(table, rows, column) =>
        for (row <- rows)
          cells(row)(column).formula =
            FormulaParsers.parse(userData(row, column))
      case ValueChanged(cell) =>
        updateCell(cell.row, cell.column)
    }
    for (row <- cells; cell <- row) listenTo(cell)
  }
  
  val rowHeader = 
    new ListView(0 until height) {
      fixedCellWidth = 35
      fixedCellHeight = table.rowHeight
    }
  
  viewportView = table
  rowHeaderView = rowHeader
  
}