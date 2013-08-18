package scala2e.chapter18.discrete_simulation

abstract class BasicCircuitSimulation extends Simulation {
  // names start with capital letter as these are constants
  def InverterDelay: Int  // abstract (defined by subclass implementation)
  def AndGateDelay: Int   // abstract
  def OrGateDelay: Int    // abstract
  
  /*
   * Wire has a list of actions that are executed once,
   * as each new action is added
   * All wire actions are executed every time the wire
   * signal state changes
   */
  class Wire {
    private var sigVal = false  // current state of signal on the wire
    
    // actions that will be executed every time the signal state changes
    private var actions: List[Action] = List()
    
    def getSignal = sigVal
    def setSignal(s: Boolean) =
      if(s != sigVal) {
        sigVal = s
        // execute all actions
        actions foreach (_ ())  // "_ ()" is shorthand for "f => f()"  
      }				// takes a fun and applies it to empty param list
    
    def addAction(a: Action) = {
      actions = a :: actions
      a()  // execute the action that was just added
    }
  }
  
  def inverter(input: Wire, output: Wire) = {
    def invertAction() {
      val inputSig = input.getSignal
      afterDelay(InverterDelay) {
        output setSignal !inputSig
      }
    }
    input addAction invertAction
  }
  
  // adds 'andAction' to  both of its input wires
  // outputs the conjunction of its input signals
  def andGate(a1: Wire, a2: Wire, output: Wire) = {
    def andAction() = {
      val a1Sig = a1.getSignal
      val a2Sig = a2.getSignal
      afterDelay(AndGateDelay) {  // curried with by-name parameter
        output setSignal (a1Sig & a2Sig)
      }
    }
    a1 addAction andAction  // add andAction to a1
    a2 addAction andAction
  }
  
  // adds 'orAction' to  both of its input wires
  // outputs the disjunction of its input signals
  def orGate(o1: Wire, o2: Wire, output: Wire) {
    def orAction() {
      val o1Sig = o1.getSignal
      val o2Sig = o2.getSignal
      afterDelay(OrGateDelay) {
        output setSignal (o1Sig | o2Sig)
      }
    }
    o1 addAction orAction
    o2 addAction orAction
  }
  
  def probe(name: String, wire: Wire) {
    def probeAction() {
      println(name + " " + currentTime + " new-value = " + wire.getSignal)
    }
    wire addAction probeAction
  }

}