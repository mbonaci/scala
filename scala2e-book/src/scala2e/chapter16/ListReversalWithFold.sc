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
  
}