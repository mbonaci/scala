package scala2e.chapter15

object WChapter15 {

  val exa = "a"                                   //> exa  : String = a
  val exb = "b"                                   //> exb  : String = b
  
  val res0 = exa match {
    case exb => println("this matched letter '" + exb + "'");
    case _ =>
      println("why is this allowed after variable pattern");
    case v =>
      println("why is this allowed after variable and wildcard patterns");
  }                                               //> this matched letter 'a'
                                                  //| res0  : Unit = ()
                                                  
                                                  
  val lst0 = List(2)                              //> lst0  : List[Int] = List(2)
  val lst1 = (lst0 :+ 2).drop(2)                  //> lst1  : List[Int] = List()
  
  
  val res1 = lst0 match {
    case List(0, _*) => println("starts with 0")
    case List(_*) => println("may be empty")
    
  }                                               //> may be empty
                                                  //| res1  : Unit = ()

  val lst2 = lst1 :+ 0                            //> lst2  : List[Int] = List(0)
  
  val res2 = lst2 match {
    case List(0, _*) => println("starts with 0")
    case List(_*) => println("may be empty")
    
  }                                               //> starts with 0
                                                  //| res2  : Unit = ()

  ("a ", 3, "-tuple") match {
    case (a, b, c) => println("matched " + a + b + c)
    case _ =>
  }                                               //> matched a 3-tuple
}