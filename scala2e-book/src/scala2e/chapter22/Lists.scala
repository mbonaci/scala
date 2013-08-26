package scala2e.chapter22

object MyListsImpl {

  def main(args: Array[String]): Unit = {
    val l = 2 :: 3 :: Nil
    println(l)  // ::(2,::(3,Nil))
  }
  
  abstract class MyList[+T] {
	def isEmpty: Boolean
	def head: T
	def tail: MyList[T]
	
	def length: Int = if (isEmpty) 0 else 1 + tail.length
	def ::[U >: T](x: U): MyList[U] = new ::(x, this)
	
	def :::[U >: T](prefix: MyList[U]): MyList[U] =
	  if (prefix.isEmpty) this
	  else prefix.head :: prefix.tail ::: this
	  
	def drop(n: Int): MyList[T] =
	  if (isEmpty) Nil
	  else if (n <= 0) this
	  else tail.drop(n - 1)

	def map[U](f: T => U): MyList[U] =
	  if (isEmpty) Nil
	  else f(head) :: tail.map(f)
  }
  
  case object Nil extends MyList[Nothing] {
	override def isEmpty = true
	def head: Nothing = throw new NoSuchElementException("head of empty list")
	def tail: MyList[Nothing] = throw new NoSuchElementException("tail of empty list")
  }
  
  final case class ::[T](head: T, tail: MyList[T]) extends MyList[T] {
    override def isEmpty: Boolean = false
  }

}