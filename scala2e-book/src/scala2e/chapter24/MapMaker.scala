package scala2e.chapter24

import scala.collection.mutable.{Map, SynchronizedMap, HashMap}

object MapMaker {
  
  def makeMap(): Map[String, String] = {
    // a synthetic subclass of HashMap that mixes in
    // SynchronizedMap is generated, an instance of it
    // is created and returned
    new HashMap[String, String] with 
        SynchronizedMap[String, String] {
      override def default(key: String) = "Why?"
      // if you ask a map to give you a key that doesn't exist
      // you'll get NoSuchElementException
      // but if you override 'default', you'll get 
      // a value returned by 'default' method
    }
    
  }
  
  def main(args: Array[String]): Unit = {
    // because our map mixes in SynchronizedMap it may be used
    // by multiple threads at the same time
    val capital = makeMap
    capital ++= List("US" -> "Washington", "Croatia" -> "Zagreb",
        "Japan" -> "Tokio")
    
    println(capital("Croatia"))
    println(capital("New Zealand"))
    
    capital += ("New Zealand" -> "Wellington")
    
    println(capital("New Zealand"))
  }
}