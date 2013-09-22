package scala2e.chapter32.discrete_simulation

import scala.actors.Actor._
import scala.actors.Actor

object Simulation {

  def main(args: Array[String]): Unit = {
    val circuit = new Circuit with Adders
    import circuit._
    
    val ain = new Wire("ain", true)
    val bin = new Wire("bin", false)
    val cin = new Wire("cin", true)
    val sout = new Wire("sout")
    val cout = new Wire("cout")
    
    probe(ain)
    probe(bin)
    probe(cin)
    probe(sout)
    probe(cout)
    
    fullAdder(ain, bin, cin, sout, cout)
    circuit.start()
  }

}


trait Adders extends Circuit {
  def halfAdder(a: Wire, b: Wire, s: Wire, c: Wire) {
    val d, e = new Wire
    orGate(a, b, d)
    andGate(a, b, c)
    inverter(c, e)
    andGate(d, e, s)
  }
  def fullAdder(a: Wire, b: Wire, cin: Wire, sum: Wire, cout: Wire) {
    val s, c1, c2 = new Wire
    halfAdder(a, cin, s, c1)
    halfAdder(b, s, sum, c2)
    orGate(c1, c2, cout)
  }
}


class Circuit {
  // consists of:
  // simulation messages                // delay constants
  // Wire and Gate classes and methods  // misc. utility methods
  val clock = new Clock
  val WireDelay = 1
  val InverterDelay = 2
  val OrGateDelay = 3
  val AndGateDelay = 3

  // message types
  case class SetSignal(sig: Boolean)
  case class SignalChanged(wire: Wire, sig: Boolean)


  // simulant that has a current state signal and a list of gates observing this state
  class Wire(name: String, init: Boolean) extends Simulant {
    def this(name: String) { this(name, false)}
    def this() { this("unnamed") }

    val clock = Circuit.this.clock  // uses the circuit's clock
    clock.add(this)  // adds itself to the clock

    private var sigVal = init
    private var observers: List[Actor] = List()

    // process messages from the clock
    def handleSimMessage(msg: Any) {
      msg match {
        // the only message type a wire can receive
        case SetSignal(s) =>
          if (s != sigVal) {
            sigVal = s
            signalObservers()
          }
      }
    }

    // propagate changes to any gates watching the wire
    def signalObservers() {
      for (obs <- observers)
        clock ! AfterDelay(
          WireDelay,
          SignalChanged(this, sigVal),
          obs)
    }

    // we need to send the initial signal state to observers, as simulation starts
    override def simStarting() { signalObservers() }

    // a wire needs a method for connecting new gates
    def addObserver(obs: Actor) {
      observers = obs :: observers
    }

    // nice string representation
    override def toString = "Wire(" + name + ")"
  }


  // since gates 'And', 'Or' and 'Not' have a lot in common:
  abstract class Gate(in1: Wire, in2: Wire, out: Wire) extends Simulant {
    val delay: Int
    def computeOutput(s1: Boolean, s2: Boolean): Boolean

    // since it mixes in 'Simulant' it's required to specify the clock
    val clock = Circuit.this.clock
    clock.add(this)

    // connect the gate to input wires
    in1.addObserver(this)
    in2.addObserver(this)

    // wires' state
    var s1, s2 = false

    // handle incoming messages that the clock sends
    def handleSimMessage(msg: Any) {
      msg match {
        case SignalChanged(w, sig) =>
          // change the state of wire
          if (w == in1)
            s1 = sig
          if (w == in2)
            s2 = sig

          // send message to output wire that the signal state changed
          clock ! AfterDelay(delay, 
            SetSignal(computeOutput(s1, s2)),
            out)
      }
    }

  }


  def orGate(in1: Wire, in2: Wire, output: Wire) =
    new Gate(in1, in2, output) {
      val delay = OrGateDelay
      def computeOutput(s1: Boolean, s2: Boolean) = s1 || s2
    }

  def andGate(in1: Wire, in2: Wire, output: Wire) =
    new Gate(in1, in2, output) {
      val delay = AndGateDelay
      def computeOutput(s1: Boolean, s2: Boolean) = s1 && s2
    }

  private object DummyWire extends Wire("dummy")  // to simplify things
  // so we can define abstract gate as a class with 2 input wires

  def inverter(input: Wire, output: Wire) =
    new Gate(input, DummyWire, output) {  // inverter, of course, uses one input wire
      val delay = InverterDelay
      def computeOutput(s1: Boolean, ignored: Boolean) = !s1
    }


  // print out state changes of wires
  def probe(wire: Wire) = new Simulant {
    val clock = Circuit.this.clock
    clock.add(this)
    wire.addObserver(this)
    def handleSimMessage(msg: Any) {
      msg match {
        case SignalChanged(w, s) =>
          println("signal " + w + " changed to " + s)
      }
    }
  }


  // to allow clients to start simulation
  def start() { clock ! Start }

}


