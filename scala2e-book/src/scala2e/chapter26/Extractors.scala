package scala2e.chapter26

object Extractors {

  def main(args: Array[String]): Unit = {
    
    // function that applies all previously defined extractors in its pattern matching code:
    def userTwiceUpper(s: String) = s match {
      case EMail(Twice(x @ UpperCase()), domain) => "match: " + x + " in domain " + domain
      case _ => "no match"
    }
    
    val res1 = userTwiceUpper("CANCAN@hotmail.com")
    val res2 = userTwiceUpper("CANCAM@hotmail.com")
    val res3 = userTwiceUpper("cancan@hotmail.com")
    
    println(res1)
    println(res2)
    println(res3)
  }
  
  
  // extractor object for e-mail addresses:
  object EMail {
    // the injection method (optional)
    def apply(user: String, domain: String) = user + "@" + domain
  
    // the extraction method (mandatory)
    def unapply(str: String): Option[(String, String)] = {
      // returns option type over pair of strings, since it must handle the case where
      // received param is not an email address
      val parts = str split "@"
      if (parts.length == 2) Some(parts(0), parts(1)) else None
    }
  }
  
  
  // extractor object for strings that consist of a substring appearing twice in a row 
  object Twice {
    def apply(s: String): String = s + s
    def unapply(s: String): Option[String] = {
      val length = s.length / 2
      val half = s.substring(0, length)
      if (half == s.substring(length)) Some(half) else None
    }
  }
  
  
  // extractor object that characterizes strings consisting of all uppercase letters:
  object UpperCase {
    def unapply(s: String): Boolean = s.toUpperCase == s
  }


}