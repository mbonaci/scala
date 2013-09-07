package scala2e.chapter24

object wksChapter24 {
  /*
  def evenElems[T](xs: Vector[T]): Array[T] = {
    val arr = new Array[T]((xs.length + 1) / 2)
    for (i <- 0 until xs.length by 2)
      arr(1 / 2) = xs(i)
    arr
  }
  */
  
  def evenElemsLong[T](xs: Vector[T])
      (implicit m: ClassManifest[T]): Array[T] = {
    val arr = new Array[T]((xs.length + 1) / 2)
    for (i <- 0 until xs.length by 2)
      arr(i / 2) = xs(i)
    arr
  }                                               //> evenElemsLong: [T](xs: Vector[T])(implicit m: ClassManifest[T])Array[T]

  def evenElems[T: ClassManifest](xs: Vector[T]): Array[T] = {
    val arr = new Array[T]((xs.length + 1) / 2)
    for (i <- 0 until xs.length by 2)
      arr(i / 2) = xs(i)
    arr
  }                                               //> evenElems: [T](xs: Vector[T])(implicit evidence$1: ClassManifest[T])Array[T]
                                                  //| 
  evenElems(Vector(1, 2, 3, 4, 5))                //> res0: Array[Int] = Array(1, 3, 5)
  evenElems(Vector("compiler", "of Scala", "is", "not", "a bitch"))
                                                  //> res1: Array[String] = Array(compiler, is, a bitch)

  // def wrap[U](xs: Vector[U]) = evenElems(xs)
  def wrap[U: ClassManifest](xs: Vector[U]) = evenElems(xs)
                                                  //> wrap: [U](xs: Vector[U])(implicit evidence$2: ClassManifest[U])Array[U]
}