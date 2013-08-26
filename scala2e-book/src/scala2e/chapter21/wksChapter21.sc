package scala2e.chapter21

object wksChapter21 {
  // weakness of this is that you cannot use it to sort list of Ints
  // because it requires that 'T' is a subtype of Odered[T]
  def maxListUpBound[T <: Ordered[T]](elements: List[T]): T =
    elements match {
      case List() => throw new IllegalArgumentException("empty")
      case List(x) => x
      case x :: rest =>
        val maxRest = maxListUpBound(rest)
        if (x > maxRest) x
        else maxRest
    }                                             //> maxListUpBound: [T <: Ordered[T]](elements: List[T])T

  // to remedy the weakness, we could add an extra argument
  // that converts 'T' to 'Ordered[T]'
  // the second param provides info about how to order 'T's
  def maxListImpParm[T](elements: List[T])
      (implicit ordered: T => Ordered[T]): T =
    elements match {
      case List() => throw new IllegalArgumentException("empty")
      case List(x) => x
      case x :: rest =>
        val maxRest = maxListImpParm(rest)(ordered)
        if (ordered(x) > maxRest) x
        else maxRest
    }                                             //> maxListImpParm: [T](elements: List[T])(implicit ordered: T => Ordered[T])T

  
  maxListImpParm(List(1, 5, 10, 3))               //> res0: Int = 10
  maxListImpParm(List(1.5, 5.2, 10.7, 3.22323))   //> res1: Double = 10.7
  maxListImpParm(List("one", "two", "three"))     //> res2: String = two

  /*
   *Because elements must always be provided explicitly in any invocation of
maxListImpParm, the compiler will know T at compile time, and can therefore
determine whether an implicit definition of type T => Ordered[T] is in
scope. If so, it can pass in the second parameter list, orderer, implicitly.
   *
   *
   */
}