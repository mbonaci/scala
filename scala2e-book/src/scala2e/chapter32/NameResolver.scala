package scala2e.chapter32
import scala.actors.Actor



object NameResolver extends Actor {
  import java.net.{InetAddress, UnknownHostException}

  def actOld() {
    react {
      case (name: String, actor: Actor) =>
        actor ! getIp(name)
        actOld()
      case "EXIT" =>
        println("Name resolver over and out.")
        // quit
      case msg =>
        println("Unhandled message: " + msg)
        actOld()
    }
  }
  
  def act() {
    loop {
      react {
        case (name: String, actor: Actor) =>
          actor ! getIp(name)
        case msg =>
          println("Unhandled message: " + msg)
      }
    }
  }

  def getIp(name: String): Option[InetAddress] = {
    try {
      Some(InetAddress.getByName(name))
    } catch {
      case _: UnknownHostException => None
    }
  }
}