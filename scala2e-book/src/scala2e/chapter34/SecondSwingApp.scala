package scala2e.chapter34

import scala.swing._
import scala.swing.event._

object SecondSwingApp extends SimpleSwingApplication {
  
  def top = new MainFrame {
    title = "Second Swing App"
    val button = new Button {
      text = "Click on me"
    }
    
    listenTo(button)
    
    val label = new Label {
      text = "No button click registered" 
    }
    
    var nClicks = 0
    reactions += {
      case ButtonClicked(b) =>
        nClicks += 1
        label.text = "Number of button clicks: " + nClicks
    }
    
    contents = new BoxPanel(Orientation.Vertical) {
      contents += button
      contents += label
      border = Swing.EmptyBorder(30, 30, 10, 30)
    }
  }
}