package scala2e.chapter16

import scala.Ordering

object MergeSort {

  def main(args: Array[String]): Unit = {
    val lst = 9 :: 1 :: 8 :: 3 :: 2 :: Nil

    val res = msort((x: Int, y: Int) => x < y)(lst)
    println(res mkString("sorted: ", ", ", ""))
    
  }
  
  def msort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (_, Nil) => xs
      case (Nil, _) => ys
      case (x :: xs1, y :: ys1) =>
        if(less(x, y)) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
    }
    
    val n = xs.length / 2
    if(n == 0) xs
    else {
      val (ys, zs) = xs splitAt n
      merge(msort(less)(ys), msort(less)(zs))
    }
  }

}