package scala2e.chapter16

object ListReversalWithFold {
  
  val lst = List(1, 2, 3, 4)                      //> lst  : List[Int] = List(1, 2, 3, 4)
  
  def flattenLeft[T](xss: List[List[T]]) =
    (List[T]() /: xss) (_ ::: _)                  //> flattenLeft: [T](xss: List[List[T]])List[T]
  
  /* If we take List(x):
	equals (by the properties of reverseLeft)
    reverseLeft(List(x))
	equals (by the template for reverseLeft)
    (List() /: List(x)) (operation)
  equals (by the definition of /:)
    operation(List(), x)
  */
  def reverseLeft[T](xs: List[T]) =
    (List[T]() /: xs){(ys, y) => y :: ys} // called "snoc" ("cons" reversed)
                                                  //> reverseLeft: [T](xs: List[T])List[T]
  
  reverseLeft(lst)                                //> res0: List[Int] = List(4, 3, 2, 1)
  
  
  // tabulation
  val squares = List.tabulate(5)(n => n * n)      //> squares  : List[Int] = List(0, 1, 4, 9, 16)
 
  squares(2)                                      //> res1: Int = 4
 
  val multiplication = List.tabulate(3, 4)(_ * _) //> multiplication  : List[List[Int]] = List(List(0, 0, 0, 0), List(0, 1, 2, 3),
                                                  //|  List(0, 2, 4, 6))
}