package scala2e.chapter17

object WordCount {

  def main(args: Array[String]): Unit = {
    val count = countWords("run forest, run fast! run forest!")
    println(count.toString)
  }

  import scala.collection.mutable
  def countWords(text: String): mutable.Map[String, Int] = {
    val wordsArray = text.split("[ !,.]+")
    val map = mutable.Map.empty[String, Int]
    
    for(w <- wordsArray)
      if(map.contains(w))
        map(w) = map(w) + 1
      else
        map += (w -> 1)
        
    map
  }
}