package scala2e.chapter32.discrete_simulation

import scala.actors.Actor
import scala.actors.Actor._

// WorkItem will be designed slightly differently than in chapter 18:
case class WorkItem(time: Int, msg: Any, target: Actor)
// action is now comprised of a message and an actor (to whom that message is sent)

// afterDelay method becomes AfterDelay message that is sent to the clock:
case class AfterDelay(delay: Int, msg: Any, target: Actor)

case class Ping(time: Int)  // sender is always the "clock"
case class Pong(time: Int, from: Actor)  // 'time' and 'from' add redundancy 

// messages requesting simulation start and stop:
case object Start
case object Stop

class Clock extends Actor {
  private var running = false
  private var currentTime = 0
  private var agenda: List[WorkItem] = List()
  private var allSimulants: List[Actor] = List()
  private var busySimulants: Set[Actor] = Set.empty

  def add(sim: Simulant) {  // we must be able to add simulants to the clock
    allSimulants = sim :: allSimulants
  }


  def act() {
    loop {
      if (running && busySimulants.isEmpty)
        // advance clock once all simulants of the current time tick finish
        advance()
      reactToOneMessage()
    }
  }


  def advance() {
    if (agenda.isEmpty && currentTime > 0) {
      // stop simulation if agenda is clear and we're not setting up
      println("** Agenda empty. Clock exiting at time " + currentTime + ".")
      self ! Stop
      return
    }
    currentTime += 1
    println("Advancing to time " + currentTime)
    processCurrentEvents()
    for (sim <- allSimulants)  // ping all simulants
      sim ! Ping(currentTime)

    busySimulants = Set.empty ++ allSimulants  // add all simulants to busy set
  }


  private def processCurrentEvents() {
    // take items that need to occur at the current time
    val todoNow = agenda.takeWhile(_.time <= currentTime)
    // remove from agenda the items we just took
    agenda = agenda.drop(todoNow.length)

    for (WorkItem(time, msg, target) <- todoNow) {
      assert(time == currentTime)
      target ! msg  // send all current items to appropriate actors
    }
  }

  def reactToOneMessage() {
    react {
      case AfterDelay(delay, msg, target) =>
        val item = WorkItem(currentTime + delay, msg, target)
        agenda = insert(agenda, item)
      case Pong(time, sim) =>
        assert(time == currentTime)
        assert(busySimulants contains sim)
        busySimulants -= sim
      case Start => running = true
      case Stop =>
        for (sim <- allSimulants)
          sim ! Stop
        exit()
    }
  }

  private def insert(ag: List[WorkItem], item: WorkItem): List[WorkItem] = {
    // when you find position, append item and rest of ag to "heads"
    if(ag.isEmpty || item.time < ag.head.time) item :: ag
    // prepend head until you find position so it's sorted by time
    else ag.head :: insert(ag.tail, item) 
  }
}


trait Simulant extends Actor {
  val clock: Clock
  def handleSimMessage(msg: Any)
  def simStarting() { }  // not abstract

  def act() {
    loop {
      react {
        case Stop => exit()
        case Ping(time) =>
          // allows subclasses to do something before they respond with 'Pong'
          if (time == 1) simStarting()  // when simulation starts running
          clock ! Pong(time, self)
        case msg => handleSimMessage(msg)
      }
    }
  }
  start()  // starts immediately, it won't do nothing until clock receives 'Send' msg
}