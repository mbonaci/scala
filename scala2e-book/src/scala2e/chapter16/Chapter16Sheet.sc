package scala2e.chapter16

object Chapter16Sheet {
  
  // 'toString' returns canonical representation of a list:
  List("a", "b", "c").toString                    //> res0: String = List(a, b, c)
  // res0: String = List(a, b, c)

  // 'mkString' is more suitable for human consumption:
  // mkString(pre, sep, post)  // returns:
  // pre + xs(0) + sep + xs(1) + sep + ... + sep + xs(xs.length - 1) + post

  // also:
  val abc = List("a", "b", "c")                   //> abc  : List[String] = List(a, b, c)
  abc mkString " - " // equals                    //> res1: String = a - b - c
  abc mkString("", " - ", "")  // also, you can omit all arguments (default to empty string)
                                                  //> res2: String = a - b - c

  // a variant of 'mkString' which appends string to a 'scala.StringBuilder' object:
  val buf = new StringBuilder                     //> buf  : StringBuilder = 
  List("a", "b", "c") addString(buf, "(", "; ", ")")
                                                  //> res3: StringBuilder = (a; b; c)
  
  val it = abc.iterator                           //> it  : Iterator[String] = non-empty iterator


  val xs = List("a", "b", "c")                    //> xs  : List[String] = List(a, b, c)
  val arr2 = new Array[String](7)                 //> arr2  : Array[String] = Array(null, null, null, null, null, null, null)
  
  xs copyToArray(arr2, 3)
  arr2                                            //> res4: Array[String] = Array(null, null, null, a, b, c, null)


  def hasZeroRow(m: List[List[Int]]) =
    m exists (row => row forall (_ == 0))         //> hasZeroRow: (m: List[List[Int]])Boolean
    
  val y = List(0, -1)                             //> y  : List[Int] = List(0, -1)
  val z = List(0, 0, 0)                           //> z  : List[Int] = List(0, 0, 0)
  val k = List()                                  //> k  : List[Nothing] = List()
  val zz = List(y, k, z)                          //> zz  : List[List[Int]] = List(List(0, -1), List(), List(0, 0, 0))
  
  k forall (_ != 0)                               //> res5: Boolean = true
  zz exists (_ == Nil)                            //> res6: Boolean = true
  
  hasZeroRow(zz)                                  //> res7: Boolean = true
  
  
  def withDefault: Option[Int] => Int = {
    case Some(x) => x
    case None => 0
  }                                               //> withDefault: => Option[Int] => Int
  withDefault(None)                               //> res8: Int = 0



  val lst = List(1, 2, 3, 4)                      //> lst  : List[Int] = List(1, 2, 3, 4)
  
  def flattenLeft[T](xss: List[List[T]]) =
    (List[T]() /: xss) (_ ::: _)                  //> flattenLeft: [T](xss: List[List[T]])List[T]

  /*
    List reversal with fold
  */
  
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
  reverseLeft(lst)                                //> res9: List[Int] = List(4, 3, 2, 1)
  
  
  // tabulation
  val squares = List.tabulate(5)(n => n * n)      //> squares  : List[Int] = List(0, 1, 4, 9, 16)
 
  squares(2)                                      //> res10: Int = 4
 
  val multiplication = List.tabulate(3, 4)(_ * _) //> multiplication  : List[List[Int]] = List(List(0, 0, 0, 0), List(0, 1, 2, 3)
                                                  //| , List(0, 2, 4, 6))

  // processing multiple lists together
  (List(4, 6, 1), List(5, 8)).zipped.map(_ * _)   //> res11: List[Int] = List(20, 48)
  
  (List("on", "mali", "debeli"), List(2, 4, 5)).zipped
    .forall(_.length == _)                        //> res12: Boolean = false
  
  (List("on", "mali", "debeli"), List(1, 4, 9)).zipped
    .exists(_.length == _)                        //> res13: Boolean = true

  (List("on", "mali", "debeli"), List(2, 4, 6)).zipped
    .exists(_.length != _)                        //> res14: Boolean = false
}