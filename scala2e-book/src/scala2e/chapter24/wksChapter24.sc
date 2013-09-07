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


  // views:
  val v = Vector(1 to 5: _*)                      //> v  : scala.collection.immutable.Vector[Int] = Vector(1, 2, 3, 4, 5)
  v map (_ + 1) map (_ * 2)                       //> res2: scala.collection.immutable.Vector[Int] = Vector(4, 6, 8, 10, 12)

  // one by one:
  val vv = v.view                                 //> vv  : scala.collection.SeqView[Int,scala.collection.immutable.Vector[Int]] =
                                                  //|  SeqView(...)
  val resInter = vv map (_ + 1)                   //> resInter  : scala.collection.SeqView[Int,Seq[_]] = SeqViewM(...)


  val res = resInter map (_ * 2)                  //> res  : scala.collection.SeqView[Int,Seq[_]] = SeqViewMM(...)

  res.force                                       //> res3: Seq[Int] = Vector(4, 6, 8, 10, 12)


  def negate(xs: collection.mutable.Seq[Int]) =
    for (i <- 0 until xs.length) xs(i) = -xs(i)   //> negate: (xs: scala.collection.mutable.Seq[Int])Unit
    
  val arr = (0 to 5).toArray                      //> arr  : Array[Int] = Array(0, 1, 2, 3, 4, 5)
  val subarr = arr.view.slice(2, 5)               //> subarr  : scala.collection.mutable.IndexedSeqView[Int,Array[Int]] = SeqView
                                                  //| S(...)
  negate(subarr)
  arr                                             //> res4: Array[Int] = Array(0, 1, -2, -3, -4, 5)
  




}