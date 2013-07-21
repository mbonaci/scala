import scala.io.Source

object Scala2e extends App {
  
  val lines = Source.fromFile(args(0)).getLines().toList
	
  if(args.length > 0) {
    val longestLine = lines.reduceLeft(
      (a, b) => if(a.length > b.length) a else b
    )
    
    val numChars = noOfChars(longestLine)
    
    for(line <- lines){
      val formattedLen = (" " * numChars + line.length.toString).takeRight(numChars)
      println(formattedLen + " | " + line)
    }

  } else
    Console.err.println("Please enter filename")


  def noOfChars(s: String) = s.length.toString.length

}