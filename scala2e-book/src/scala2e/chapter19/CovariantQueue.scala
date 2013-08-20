package scala2e.chapter19

/*
 * Purely functional Queue that performs at most one trailing
 * to leading adjustment for any sequence of head operations
 * Yes, it has reassignable fields, but they are private,
 * thus invisible to any client using the class
 */
class CovariantQueue[+T] private (
    private[this] var leading: List[T],  // object private vars
    private[this] var trailing: List[T]
    // without [this]:
    // error: covariant type T occurs in contravariant position
    // in type List[T] of parameter of setter leading_=
  ) {
  private def mirror() =
    if (leading.isEmpty) {
      while (!trailing.isEmpty) {
        leading = trailing.head :: leading
        trailing = trailing.tail
      }
    }
  
  def head: T = {
    mirror()
    leading.head
  }
  
  def tail: CovariantQueue[T] = {
    mirror()
    new CovariantQueue(leading.tail, trailing)
  }
  
  def enqueue[U >: T](x: U) =
    new CovariantQueue[U](leading, x :: trailing)

}