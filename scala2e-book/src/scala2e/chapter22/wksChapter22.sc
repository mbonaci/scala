package scala2e.chapter22

object wksChapter22 {
  abstract class Fruit
  class Apple extends Fruit
  class Orange extends Fruit
  
  val apples = new Apple :: Nil                   //> apples  : List[scala2e.chapter22.wksChapter22.Apple] = List(scala2e.chapter2
                                                  //| 2.wksChapter22$Apple@46e337b2)
  val fruits = new Orange :: apples               //> fruits  : List[scala2e.chapter22.wksChapter22.Fruit] = List(scala2e.chapter2
                                                  //| 2.wksChapter22$Orange@42e57c4c, scala2e.chapter22.wksChapter22$Apple@46e337b
                                                  //| 2)
}