package scala2e.chapter20

object AbstractRational {

  trait RationalTrait {
    val numerArg: Int
    val denomArg: Int
  }
  
  def main(args: Array[String]): Unit = {
    new RationalTrait {
      val numerArg = 1
      val denomArg = 2
    }
  }
}