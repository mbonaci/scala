package scala2e.chapter32

import scala.actors.Actor

object Parrot extends Actor {
  def act() {
    while (true) {
      receive {
        case msg => println("received message: " + msg)
      }
    }
  }
}