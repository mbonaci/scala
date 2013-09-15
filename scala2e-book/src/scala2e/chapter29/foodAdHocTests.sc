package scala2e.chapter28

import scala2e.chapter28.recipesApp.tests.SimpleDatabase
import scala2e.chapter28.recipesApp.tests.SimpleBrowser

object foodAdHocTests {
  val apple = SimpleDatabase.foodNamed("Apple").get
                                                  //> apple  : scala2e.chapter28.recipesApp.domain.Food = Apple
  SimpleBrowser.recipesUsing(apple)               //> res0: List[scala2e.chapter28.recipesApp.domain.Recipe] = List(fruit salad)






}