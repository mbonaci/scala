package scala2e.chapter30

import scala.collection.mutable.HashSet

object wksChapter30 {
    val p = new Point(1, 2)                       //> p  : scala2e.chapter30.Point = scala2e.chapter30.Point@6bc
    val cp = new ColoredPoint(1, 2, Color.Red)    //> cp  : scala2e.chapter30.ColoredPoint = scala2e.chapter30.ColoredPoint@6bc
    p equals cp                                   //> res0: Boolean = true
    cp equals p                                   //> res1: Boolean = true
    
    HashSet[Point](p) contains cp                 //> res2: Boolean = true
    HashSet[Point](cp) contains p                 //> res3: Boolean = true
    
    
    val redp = new ColoredPoint(1, 2, Color.Red)  //> redp  : scala2e.chapter30.ColoredPoint = scala2e.chapter30.ColoredPoint@6bc
                                                  //| 
    val blup = new ColoredPoint(1, 2, Color.Blue) //> blup  : scala2e.chapter30.ColoredPoint = scala2e.chapter30.ColoredPoint@6bc
                                                  //| 
    redp == p                                     //> res4: Boolean = true
    p == blup                                     //> res5: Boolean = true
    redp == blup                                  //> res6: Boolean = false

}