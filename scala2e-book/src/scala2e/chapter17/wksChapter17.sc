package scala2e.chapter17

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ArrayBuffer

object wksChapter17 {
  val buf = new ListBuffer[Int]                   //> buf  : scala.collection.mutable.ListBuffer[Int] = ListBuffer()
  buf += 22                                       //> res0: scala2e.chapter17.wksChapter17.buf.type = ListBuffer(22)
  11 +=: buf                                      //> res1: scala2e.chapter17.wksChapter17.buf.type = ListBuffer(11, 22)
  buf.toList                                      //> res2: List[Int] = List(11, 22)


  val abuf = new ArrayBuffer[Int]()               //> abuf  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer()
  // append using '+='
  abuf += 8                                       //> res3: scala2e.chapter17.wksChapter17.abuf.type = ArrayBuffer(8)
  abuf += 4                                       //> res4: scala2e.chapter17.wksChapter17.abuf.type = ArrayBuffer(8, 4)
  abuf.length                                     //> res5: Int = 2
  abuf(1)                                         //> res6: Int = 4
 
 
  def hasUpperCaseLetter(s: String) = s.exists(_.isUpper)
                                                  //> hasUpperCaseLetter: (s: String)Boolean
  hasUpperCaseLetter("glupson 1")                 //> res7: Boolean = false
  hasUpperCaseLetter("glupson 1A")                //> res8: Boolean = true
  
  
  // sets
  val text = "run Forrest, run. That's it Forrest! Run!"
                                                  //> text  : String = run Forrest, run. That's it Forrest! Run!
  val wordsArray = text.split("[ !,.]+")          //> wordsArray  : Array[String] = Array(run, Forrest, run, That's, it, Forrest, 
                                                  //| Run)
  import scala.collection.mutable
  val set = mutable.Set.empty[String]             //> set  : scala.collection.mutable.Set[String] = Set()
 
  for(word <- wordsArray)
    set += word.toLowerCase
    
  set                                             //> res9: scala.collection.mutable.Set[String] = Set(it, run, that's, forrest)

  val w = mutable.Map.empty[String, Int]          //> w  : scala.collection.mutable.Map[String,Int] = Map()
  w += ("a" -> 1)                                 //> res10: scala2e.chapter17.wksChapter17.w.type = Map(a -> 1)
  w.clear
  w                                               //> res11: scala.collection.mutable.Map[String,Int] = Map()



  import scala.collection.immutable.TreeSet
  val ts = TreeSet(9, 2, 5, 1, 8, 6, 4, 3)        //> ts  : scala.collection.immutable.TreeSet[Int] = TreeSet(1, 2, 3, 4, 5, 6, 8,
                                                  //|  9)

  import scala.collection.immutable.TreeMap
  val tm = TreeMap(8 -> 'e', 7 -> 'a', 1 -> 'w')  //> tm  : scala.collection.immutable.TreeMap[Int,Char] = Map(1 -> w, 7 -> a, 8 -
                                                  //| > e)
  val otm = tm + (2 -> 'x')                       //> otm  : scala.collection.immutable.TreeMap[Int,Char] = Map(1 -> w, 2 -> x, 7 
                                                  //| -> a, 8 -> e)



}