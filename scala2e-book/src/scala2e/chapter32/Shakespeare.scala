package scala2e.chapter32
import scala.actors.Actor._

object ActorActing {

//  val desdemona = actor {
//    for (i <- 1 to 3) {
//      print("I rule! ")
//      Thread.sleep(1000)
//    }
//  }
  
  def main(args: Array[String]): Unit = {
//    Shakespeare.start()
//    Thread.sleep(1000)
//    Hamlet.start()
//    desdemona.start()
    Parrot.start()
    
    val desdemona = actor {
      for (i <- 1 to 3) {
        print("I rule! ")
        Thread.sleep(1000)
      }
      println
      Thread.sleep(4000)
      Parrot ! "oh my darling"
      Thread.sleep(2000)
      Parrot ! "oh my darling"
      Thread.sleep(2000)
      Parrot ! "oh my darling Clementine"
    }
  }

}


import scala.actors._
object Shakespeare extends Actor {
  def act() {
    for (i <- 1 to 5) {
      println("To be or not to be. Is that a question?")
      Thread.sleep(2000)
    }
  }
}

