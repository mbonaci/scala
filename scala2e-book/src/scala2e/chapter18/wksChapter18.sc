package scala2e.chapter18

object wksChapter18 {
 
  val t = new Time                                //> t  : scala2e.chapter18.Time = scala2e.chapter18.Time@49a1afb1
  t.hour_=(13)
  t.hour                                          //> res0: Int = 13

  t.minute_=(1)



  val therm = new Thermometer                     //> therm  : scala2e.chapter18.Thermometer = 32.0 Â°F/0.0 Â°C
  println(therm)                                  //> 32.0 Â°F/0.0 Â°C
  
  therm.fahrenheit_=(100)
  println(therm)                                  //> 100.0 Â°F/37.77 Â°C
  



}