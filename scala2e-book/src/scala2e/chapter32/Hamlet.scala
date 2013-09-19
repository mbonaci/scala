package scala2e.chapter32
import scala.actors._

object Hamlet extends Actor {
  def act() {
    for (i <- 1 to 5) {
      println("Yes, that was a question.")
      Thread.sleep(3000)
    }
  }
}