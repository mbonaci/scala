package scala2e.chapter23

object ReversedTranslationDemo {

  def main(args: Array[String]): Unit = {
    val xs = List(1, 2, 3, 4)
    def f1 = (x: Int) => x + 1
    def f2 = (x: Int) => (x. to (x + 1)).toList
    def f3 = (x: Int) => x % 2 == 0
    
    val mapped = map(xs, f1)
    val flatmapped = flatMap(xs, f2)
    val filtered = filter(xs, f3)
    
    println(mapped)
    println(flatmapped)
    println(filtered)
  }

  def map[A, B](xs: List[A], f: A => B): List[B] =
    for (x <- xs) yield f(x)
  
  def flatMap[A, B](xs: List[A], f: A => List[B]): List[B] =
    for (x <- xs; y <- f(x)) yield y
    
  def filter[A](xs: List[A], p: A => Boolean): List[A] =
    for (x <- xs if p(x)) yield x
}