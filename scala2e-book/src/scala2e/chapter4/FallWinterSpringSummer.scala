package scala2e.chapter4

import ChecksumAccumulator.calculate

object FallWinterSpringSummer extends App {

  for(season <- List("fall", "winter", "spring"))
    println(season + ": " + calculate(season));

}