package scala2e.chapter4

object Ch9 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(72); 
  
  var assertionsEnabled = true;System.out.println("""assertionsEnabled  : Boolean = """ + $show(assertionsEnabled ));$skip(120); 
  
  def myAssert(predicate: () => Boolean) =
    if (assertionsEnabled && !predicate())
      throw new AssertionError;System.out.println("""myAssert: (predicate: () => Boolean)Unit""");$skip(33); 

  val a = myAssert(() => 5 > 3);System.out.println("""a  : Unit = """ + $show(a ));$skip(119); 
  
  def byNameAssert(predicate: => Boolean) =
    if (assertionsEnabled && !predicate)
      throw new AssertionError;System.out.println("""byNameAssert: (predicate: => Boolean)Unit""");$skip(33); 
  
  val b = byNameAssert(5 > 3);System.out.println("""b  : Unit = """ + $show(b ))}
}
