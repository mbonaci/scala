package scala2e.chapter4

import scala2e.chapter4.ChecksumAccumulator.calculate

object FallWinterSpringSummer extends App {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(223); 

  for(season <- List("fall", "winter", "spring"))
    println(season + ": " + calculate(season));}

}
