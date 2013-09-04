package scala2e.chapter24

object BinaryTree {

  def main(args: Array[String]): Unit = {}

  sealed abstract class PlainTree
  case class Branch(left: Tree, right: Tree) extends Tree
  case class Node(elem: Int) extends Tree
  
  // now assume you want to make trees traversable:
  sealed abstract class Tree extends Traversable[Int] {
    
    def foreach[U](f: Int => U) = this match {
      case Node(elem) => f(elem)
      case Branch(l, r) => l foreach f; r foreach f
    }
  }
  
  // less efficient IterableTree, to make iterable
  sealed abstract class IterableTree extends Iterable[Int] {
    def iterator: Iterator[Int] = this match {
      case IterableNode(elem) => Iterator.single(elem)
      case IterableBranch(l, r) => l.iterator ++ r.iterator
    }
  }
  
  case class IterableBranch(left: IterableTree, right: IterableTree) extends IterableTree
  case class IterableNode(elem: Int) extends IterableTree
}