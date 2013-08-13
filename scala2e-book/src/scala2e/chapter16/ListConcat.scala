package scala2e.chapter16

object ListConcat {

  def main(args: Array[String]): Unit = {
    val x = 1 :: 2 :: 3 :: Nil
    val y = 4 :: 5 :: 6 :: Nil
    
    val z = append(x, y)
    
    println(z.mkString(" :: ") + " :: Nil")
    println(rev(z).mkString(" :: ") + " :: Nil")
  }
  
  // concatenation implementation
  def append[T](xs: List[T], ys: List[T]): List[T] = xs match {
    case List() => ys
    case x :: rest => x :: append(rest, ys)
  }

  // 'reverse' implemented using concatenation
  def rev[T](xs: List[T]): List[T] = xs match {
  	case List() => xs
  	case x :: rest => append(rev(rest), List(x))
  }
}