package scala2e.chapter4

import ChecksumAccumulator.calculate


object Fall {

  calculate("s")                                  //> res0: Int = -115

  println("""|Welcome to Ultamix 3000.
             |Type "HELP" for help.""".stripMargin)
                                                  //> Welcome to Ultamix 3000.
                                                  //| Type "HELP" for help.

  val res = 11 % 4                                //> res  : Int = 3
  val rez = 11.0 % 4                              //> rez  : Double = 3.0
  val ie754 = math.IEEEremainder(11.0, 4.0)       //> ie754  : Double = -1.0
  val not = !(1 > 2)                              //> not  : Boolean = true


}