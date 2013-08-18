package scala2e.chapter18.discrete_simulation

abstract class Simulation {
  type Action = () => Unit  // sugar
  
  case class WorkItem(time: Int, action: Action)
  
  private var curtime = 0
  def currentTime: Int = curtime
  
  private var agenda: List[WorkItem] = List()
  
  private def insert(ag: List[WorkItem], item: WorkItem): List[WorkItem] = {
    // when you find position, append item and rest of ag to "heads"
    if(ag.isEmpty || item.time < ag.head.time) item :: ag
    // prepend head until you find position so it's sorted by time
    else ag.head :: insert(ag.tail, item) 
  }
  
  // curried function with by-name parameter block
  def afterDelay(delay: Int)(block: => Unit) {  // passing block by-name
    val item = WorkItem(currentTime + delay, () => block)
    agenda = insert(agenda, item)
  }
  
  private def next() {
    (agenda: @unchecked) match {  // ignore unhandled cases
      case item :: rest =>   // take first and separate from rest
        agenda = rest  		 // remove first work item from agenda
        curtime = item.time  // set current time to be the item's time
        item.action()		 // execute work item
    }
  }
  
  def run() {
    afterDelay(0){
      // executed in afterDelay, not before passing (call by-name param)
      println("*** simulation started, time = " + currentTime + " ***")
    }
    while (!agenda.isEmpty) next()
  }

}