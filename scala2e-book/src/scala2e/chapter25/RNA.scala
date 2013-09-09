package scala2e.chapter25

object RNA {
  def main(args: Array[String]): Unit = {
    val xs = List(A, G, T, A)
    RNA1.fromSeq(xs)
    val rna1 = RNA1(A, U, G, G, T)
    
    println("_____ RNA1 _____")
    println(rna1.length)
    println(rna1.last)
    println(rna1.take(3))
    
    RNA2.fromSeq(xs)
    val rna2 = RNA2(A, U, G, G, T)
    
    println
    println("_____ RNA2 _____")
    println(rna2.length)
    println(rna2.last)
    println(rna2.take(3))
    println
    
    val rna = RNA2(A, U, G, G, T)
    val res1 = rna map { case A => T case b => b}  // RNA(T, U, G, G, T)
    val res2 = rna ++ rna  // RNA(A, U, G, G, T, A, U, G, G, T)
    
    // but mapping to some other type:
    val res3 = rna map Base.toInt  // IndexedSeq[Int] = Vector(0, 3, 2, 2, 1)
    val res4 = rna ++ List("ie", "eg")  // IndexedSeq[java.lang.Object] = Vector(A, U, G, G, T, ie, eg)
    
    println(res1)
    println(res2)
    println(res3)
    println(res4)
  }
}

abstract class Base
case object A extends Base
case object T extends Base
case object G extends Base
case object U extends Base

object Base {
  val fromInt: Int => Base = Array(A, T, G, U)
  val toInt: Base => Int = Map(A -> 0, T -> 1, G -> 2, U -> 3)
}

// RNA strands class, v1
import collection.IndexedSeqLike
import collection.mutable.{Builder, ArrayBuffer}
import collection.generic.CanBuildFrom

// RNA strands can be very long, so we're building our own collection to optimize
// since there are only 4 bases, a base can be uniquely identified with 2 bits
// so you can store 16 bases in an integer
// we'll create a specialized subclass of 'Seq[Base]'

// 'groups' represents packed bases (16 in each array elem, except maybe in last)
// 'length' specifies total number of bases on the array
// 'private', so clients cannot instantiate it with 'new' (hiding implementation)
final class RNA1 private (val groups: Array[Int], val length: Int)  // parametric fields
    extends IndexedSeq[Base] {  // 'IndexedSeq' has 'length' and 'apply' methods
  import RNA1._
  def apply(idx: Int): Base = {
    if (idx < 0 || length <= idx)
      throw new IndexOutOfBoundsException

    // extract int value from the 'groups', then extract 2-bit number
    Base.fromInt(groups(idx / N) >> (idx % N * S) & M)
  }
//  override def take(count: Int): RNA1 = RNA1.fromSeq(super.take(count))
}

object RNA1 {
  private val S = 2             // number of bits necessary to represent group
  private val N = 32            // number of groups that fit into Int
  private val M = (1 << S) - 1  // bitmask to isolate a group (lowest S bits in a word)

  // converts given sequence of bases to instance of RNA1
  def fromSeq(buf: Seq[Base]): RNA1 = {
    val groups = new Array[Int]((buf.length + N - 1) / N)
    for (i <- 0 until buf.length)  // packs all the bases
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)  // bitwise-or equals

    new RNA1(groups, buf.length)
  }
  def apply(bases: Base*) = fromSeq(bases)
}




final class RNA2 private (val groups: Array[Int], val length: Int)
    extends IndexedSeq[Base] with IndexedSeqLike[Base, RNA2] {
  import RNA2._
  override def newBuilder: Builder[Base, RNA2] =
    new ArrayBuffer[Base] mapResult fromSeq
    
  def apply(idx: Int): Base = {
    if (idx < 0 || length <= idx)
      throw new IndexOutOfBoundsException

    // extract int value from the 'groups', then extract 2-bit number
    Base.fromInt(groups(idx / N) >> (idx % N * S) & M)
  }
}

object RNA2 {
  private val S = 2             // number of bits necessary to represent group
  private val N = 32            // number of groups that fit into Int
  private val M = (1 << S) - 1  // bitmask to isolate a group (lowest S bits in a word)

  // converts given sequence of bases to instance of RNA1
  def fromSeq(buf: Seq[Base]): RNA2 = {
    val groups = new Array[Int]((buf.length + N - 1) / N)
    for (i <- 0 until buf.length)  // packs all the bases
      groups(i / N) |= Base.toInt(buf(i)) << (i % N * S)  // bitwise-or equals

    new RNA2(groups, buf.length)
  }
  def apply(bases: Base*) = fromSeq(bases)
}