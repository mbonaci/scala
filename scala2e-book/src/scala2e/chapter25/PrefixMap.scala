package scala2e.chapter25

object PreMap {

  def main(args: Array[String]): Unit = {
    val m = PrefixMap("abc" -> 0, "abd" -> 1, "al" -> 2, "all" -> 2, "xy" -> 4)
  }
  

  // PrefixMap implementation (Patricia trie based)
  import collection._
  class PrefixMap[T] 
      extends mutable.Map[String, T] 
      with mutable.MapLike[String, T, PrefixMap[T]] {
    var suffixes: immutable.Map[Char, PrefixMap[T]] = Map.empty
    var value: Option[T] = None
  
    def get(s: String): Option[T] =
      if (s.isEmpty) value
      else suffixes get (s(0)) flatMap (_.get(s substring 1))
  
    def withPrefix(s: String): PrefixMap[T] =
      if (s.isEmpty) this
      else {
        val leading = s(0)
        suffixes get leading match {
          case None => suffixes = suffixes + (leading -> empty)
          case _ =>
        }
        suffixes(leading) withPrefix (s substring 1)
      }
  
    override def update(s: String, elem: T) =
      withPrefix(s).value = Some(elem)
    override def remove(s: String): Option[T] =
      if (s.isEmpty) { val prev = value; value = None; prev}
      else suffixes get (s(0)) flatMap (_.remove(s substring 1))
      
    def iterator: Iterator[(String, T)] =
      (for (v <- value.iterator) yield ("", v)) ++
      (for ((chr, m) <- suffixes.iterator;
          (s, v) <- m.iterator) yield (chr +: s, v))
  
    def += (kv: (String, T)): this.type = { update(kv._1, kv._2); this }
    def -= (s: String): this.type = { remove(s); this }
    override def empty = new PrefixMap[T]  
  }
  
  
  
  import scala.collection.mutable.{Builder, MapBuilder}
  import scala.collection.generic.CanBuildFrom
  
  object PrefixMap extends {
    def empty[T] = new PrefixMap[T]
    
    def apply[T](kvs: (String, T)*): PrefixMap[T] = {
      val m: PrefixMap[T] = empty
      for (kv <- kvs) m += kv
      m
    }
    
    def newBuilder[T]: Builder[(String, T), PrefixMap[T]] =
      new MapBuilder[String, T, PrefixMap[T]](empty)
      
    implicit def canBuildFrom[T]: CanBuildFrom[PrefixMap[_], (String, T), PrefixMap[T]] =
      new CanBuildFrom[PrefixMap[_], (String, T), PrefixMap[T]] {
        def apply(from: PrefixMap[_]) = newBuilder[T]
        def apply() = newBuilder[T]
    }
  }
}