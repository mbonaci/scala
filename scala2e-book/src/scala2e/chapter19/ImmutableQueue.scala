package scala2e.chapter19

/*
Represent a queue by two lists, called leading and trailing. 
The leading list contains elements towards the front, 
whereas the trailing list contains elements towards the
back of the queue in reversed order.
The contents of the whole queue are at each instant equal 
to “leading ::: trailing.reverse”.
Now, to append an element, you just cons it to the trailing list
using the :: operator, so enqueue is constant time.
This means that, when an initially empty queue is constructed
from successive enqueue operations, the trailing list will grow,
whereas the leading list will stay empty. Then, before the first
head or tail operation is performed on an empty leading list, 
the whole trailing list is copied to leading, reversing the order
of the elements. This is done in an operation called mirror.

  head    - returns the first element of the queue
  tail    - returns a queue without its first element
  enqueue - returns a new queue with a given element appended at the end
*/

private class ImmutableQueue[T] (
    private val leading: List[T],
    private val trailing: List[T]
  ) {
  private var dirty = true
  
  def head =
    if(dirty)
      mirror.leading.head
    else
      leading.head  
  
  def tail = {
    val iq = mirror
    new ImmutableQueue(iq.leading.tail, iq.trailing)
  } 
  
  def enqueue(x: T) = new ImmutableQueue(leading, x :: trailing)

  private def mirror = {
    dirty = false
    if(leading.isEmpty)
      new ImmutableQueue(trailing.reverse, Nil)
    else
      this
  }
    
  
}