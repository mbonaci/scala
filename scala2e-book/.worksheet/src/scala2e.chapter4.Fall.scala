package scala2e.chapter4

import ChecksumAccumulator.calculate


object Fall {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(96); val res$0 = 

  calculate("s");System.out.println("""res0: Int = """ + $show(res$0));$skip(92); 

  println("""|Welcome to Ultamix 3000.
             |Type "HELP" for help.""".stripMargin);$skip(20); 

  val res = 11 % 4;System.out.println("""res  : Int = """ + $show(res ));$skip(21); 
  val rez = 11.0 % 4;System.out.println("""rez  : Double = """ + $show(rez ));$skip(44); 
  val ie754 = math.IEEEremainder(11.0, 4.0);System.out.println("""ie754  : Double = """ + $show(ie754 ));$skip(21); 
  val not = !(1 > 2);System.out.println("""not  : Boolean = """ + $show(not ))}


}
