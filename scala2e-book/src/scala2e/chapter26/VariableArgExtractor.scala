package scala2e.chapter26

import scala2e.chapter26.Extractors.EMail

object VariableArgExtractor {

  def main(args: Array[String]): Unit = {
    val s = "luka.bonaci@zg.htnet.com.hr"
    
    println(isLukaBonaciInDotHr(s))
    
    val dom = Domain.apply("luka", "bonaci@zg", "htnet", "com", "hr")
    println(dom.mkString)
    
    s match {  // domains are in reverse order so it better fits sequence patterns
      case Domain("org", "acm", _*) => println("acm.org")
      case Domain("com", "sun", "java", _*) => println("java.sun.com")
      case Domain("net", _*) => println("a .net domain")
      case Domain("hr", "com", _*) => println("welcome to Croatia")
      case _ => println("please stop!")
    }
        
    val ExpandedEMail(name, topdom, subdoms @ _*) = s
    println(name + " from " + topdom + " at " + subdoms.mkString("."))
  }

  object Domain {
    // the injection (optional)
    def apply(parts: String*): String =
      parts.reverse.mkString(".")
  
    // the extraction (mandatory)
    // first splits on periods, then reverses and wraps in 'Some'
    def unapplySeq(whole: String): Option[Seq[String]] = { // must return 'Option[Seq[T]]'
      Some(whole.split("\\.").reverse)
    }
  }
  
  // to search for an email address named "luka" in some ".hr" domain:
  def isLukaBonaciInDotHr(s: String): Boolean = s match {
    case EMail("luka.bonaci", Domain("hr", _*)) => true
    case _ => false
  }
  
  // it's also possible to return some fixed elements from 'unapplySeq', together with the
  // variable part, which is expressed by returning all elements in a tuple, where the
  // variable part comes last, as usual
  // e.g. extractor for emails where the domain part is already expanded into sequence:
  object ExpandedEMail {
    // returns optional value of a pair (Tuple2), where the first part is the user, and 
    // the second part is a sequence of names representing the domain
    def unapplySeq(email: String): Option[(String, Seq[String])] = {
      val parts = email split "@"
      if (parts.length == 2)
        Some(parts(0), parts(1).split("\\.").reverse)
      else
        None
    }
  }

}