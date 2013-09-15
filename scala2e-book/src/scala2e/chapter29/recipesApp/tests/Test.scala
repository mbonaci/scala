package scala2e.chapter29.recipesApp.tests

import scala2e.chapter29.recipesApp.domain.Food
import scala2e.chapter29.recipesApp.domain.Recipe

object Test {
  def main(args: Array[String]): Unit = {}
}


object Apple extends Food("Apple")
object Orange extends Food("Orange")
object Cream extends Food("Cream")
object Sugar extends Food("Sugar")

object FruitSalad extends Recipe(
  "fruit salad",
  List(Apple, Orange, Cream, Sugar),
  "Shake em up."
)