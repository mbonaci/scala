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

}