package scala2e.chapter28

import scala2e.chapter29.recipesApp.tests.SimpleDatabase
import scala2e.chapter29.recipesApp.tests.SimpleBrowser
import scala2e.chapter29.recipesApp.tests.StudentDatabase

object foodAdHocTests {
  val apple = SimpleDatabase.foodNamed("Apple").get
                                                  //> apple  : scala2e.chapter29.recipesApp.domain.Food = Apple
  SimpleBrowser.recipesUsing(apple)               //> res0: List[scala2e.chapter29.recipesApp.domain.Recipe] = List(fruit salad)


 
  val category = StudentDatabase.allCategories.head
                                                  //> category  : scala2e.chapter29.recipesApp.tests.StudentDatabase.FoodCategory 
                                                  //| = FoodCategory(edible,List(FrozenFood))
  //SimpleBrowser.displayCategory(category)
      // completely different types!!!
      // type mismatch:
      // found    : StudentDatabase.FoodCategory
      // required : SimpleDatabase.FoodCategory
}