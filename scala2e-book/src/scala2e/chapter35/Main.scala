package scala2e.chapter35

import swing._

object Main extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "ScalaSheet"
    contents = new Spreadsheet(20, 10)
  }

}