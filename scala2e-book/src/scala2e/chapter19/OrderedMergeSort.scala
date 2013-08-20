package scala2e.chapter19

object OrderedMergeSort {
  
  // requires that passed list type mixes in Ordered trait
  def orderedMergeSort[T <: Ordered[T]](xs: List[T]): List[T] = {
    def merge(xs: List[T], ys: List[T]): List[T] =
      (xs, ys) match {
      	case (Nil, _) => ys
      	case (_, Nil) => xs
      	case (x :: xs1, y :: ys1) =>
      	  if (x < y) x :: merge(xs1, ys)
      	  else y :: merge(xs, ys1)
      }
    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (ys, zs) = xs splitAt n
      merge(orderedMergeSort(ys), orderedMergeSort(zs))
    }
  }

}