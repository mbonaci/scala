package scala2e.chapter09

object ControlAbstraction {

  def main(args: Array[String]): Unit = {
//    val files = FileMatcher.filesEnding(".scala")
//    for(file <- files) println("Ends: " + file.getPath)
//    
//    val fref = FileMatcherRefactored.filesContaining("ner")
//    for(file <- fref) println("Contains: " + file.getPath)
//    
//    val fref2 = FileMatcherRefactored2.filesRegex(".*.sc*")
//    for(file <- fref) println("Regex: " + file.getPath)
    
    val lst = List(0, 2, 4, 992, 12)
    println(ClientCode.containsOddNum(lst))
  }

  private def filesHere = (new java.io.File("src")).listFiles
  
  object FileMatcher {
    
    
	def filesEnding(query: String) =
	  for (
	    file <- filesHere;
	    if file.getName.endsWith(query)
	  ) yield file
	  
	def filesContaining(query: String) =
	  for(
	    file <- filesHere;
	    if file.getName.contains(query)
	  ) yield file
	
	def filesRegex(query: String) =
	  for(
	    file <- filesHere;
	    if file.getName.matches(query)
	  ) yield file

  }
  
    
  // pass a function value that calls the method
  object FileMatcherRefactored {
    def filesMatching(query: String, matcher: (String, String) => Boolean) = {
      for(
        file <- filesHere;
        if matcher(file.getName, query)
      ) yield file
    }
    
    def filesEnding(query: String) =
      filesMatching(query, _.endsWith(_))
      
    def filesContaining(query: String) = 
      filesMatching(query, _.contains(_))
      
    def filesRegex(query: String) =
      filesMatching(_: String, _.matches(_))
  }
  
  object FileMatcherRefactored2 {
    def filesMatching(matcher: String => Boolean) = {
      for(
        file <- filesHere;
        if matcher(file.getName)
      ) yield file
    }
    
    def filesEnding(query: String) =
      filesMatching(_.endsWith(query))
      
    def filesContaining(query: String) = 
      filesMatching(_.contains(query))
      
    def filesRegex(query: String) =
      filesMatching(_.matches(query))
  }
  
  object ClientCode {
    def containsOddNum(nums: List[Int]) = nums.exists(_ % 2 == 1)
  }
}