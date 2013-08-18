package scala2e.chapter18

class Thermometer {
  var celsius: Float = _  // initialized to default value (0)

  def fahrenheit = celsius * 9 / 5 + 32

  def fahrenheit_= (f: Float) {
    celsius = (f - 32) * 5 / 9
  }

  override def toString = {
    val c = celsius.toString
    val cRound = 
      if(c.contains('.') && c.length > c.indexOf(".") + 3) {
        c.take(c.indexOf(".") + 3)
      } else
        c
    
    val f = fahrenheit.toString
    val fRound = 
      if(f.contains('.') && f.length > f.indexOf(".") + 3) {
        f.take(f.indexOf(".") + 3)
      } else
        f
        
    fRound + " °F/" + cRound + " °C"
  }
}

object Thermometer {
  def main(args: Array[String]): Unit = {
    val therm = new Thermometer                     //> therm  : scala2e.chapter18.Thermometer = 32.0F/0.0C
  
    therm.fahrenheit_=(927312)
    println("°F: " + therm.fahrenheit)
    println("°C: " + therm.celsius)
    println(therm + "\n")
    
    therm.fahrenheit_=(-252)
    println("°F: " + therm.fahrenheit)
    println("°C: " + therm.celsius)
    println(therm + "\n")
    
    therm.fahrenheit_=(-39)
    println("°F: " + therm.fahrenheit)
    println("°C: " + therm.celsius)
    println(therm + "\n")
    
    therm.fahrenheit_=(-39.369768668.toFloat)
    println("°F: " + therm.fahrenheit)
    println("°C: " + therm.celsius)
    println(therm)
  }
}