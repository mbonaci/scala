package scala2e.chapter18

class Time {
  private[this] var h = 12
  private[this] var m = 0
  
  def hour: Int = h
  def hour_= (x: Int) {
    require(0 <= x && x < 24)
    h = x
  }
  
  def minute = m
  def minute_= (x: Int) {
    require(0 <= x && x < 60)
    m = x
  }
}