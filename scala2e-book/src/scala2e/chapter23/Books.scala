package scala2e.chapter23

object Books {

  def main(args: Array[String]): Unit = {
    // to find the titles of all books whose author's first name starts with "A":
	val aAuthors =
	  for (b <- books;
	    a <- b.authors
	    if a startsWith "A"
      ) yield b.title
	
    // to find the titles of all books that have "JavaScript" in title
    val js =
      for (b <- books if (b.title indexOf "JavaScript") >= 0)
      yield b.title
      
    // to find the names of all authors that have written at least 2 books
    val two = 
      for (b1 <- books; 
           b2 <- books if b1 != b2;
           a1 <- b1.authors; a2 <- b2.authors if a1 == a2)
      yield a1
    
    def removeDuplicates[A](xs: List[A]): List[A] = {
      if (xs.isEmpty) xs
      else  // take head and compare with all in tail
    	  	// then repeat the same thing with tail
        xs.head :: removeDuplicates(
          for (x <- xs.tail if x != xs.head) yield x
        )
        
       // the same thing with filter
       // remove from tail if element equals head
       // xs.head :: removeDuplicates(
       //   xs.tail filter (x => x != xs.head)
       // )
        
     
    }
    println(removeDuplicates(two))
  }

  
  case class Book(title: String, authors: String*)

val books: List[Book] = 
  List(
    Book(
      "Essential JavaScript design patterns", "Addi Osmani"),
    Book(
      "Developing backbone.js applications", "Addi Osmani"),
    Book(
      "Effective JavaScript", "Dave Herman"),
    Book(
      "JavaScript: The good parts", "Douglas Crockford"),
    Book(
      "AngularJS", "Brad Green", "Shyam Seshadri"),
    Book(
      "Taming text", "Grant S. Ingersoll", "Thomas S. Morton", "Andrew L. Farris"),
    Book(
      "Graph Databases", "Ian Robinson", "Jim Webber", "Emil Eifrem"),
    Book(
      "Node.js in action", "Mike Cantelon", "TJ Holowaychuk", "Nathan Rajlich"),
    Book(
      "ClojureScript up and running", "Stuart Sierra", "Luke VanderHart")
  )

}