package scala2e.chapter29.recipesApp.tests

import scala2e.chapter29.recipesApp.application.Database
import scala2e.chapter29.recipesApp.domain.Recipe

object SimpleDatabase extends Database {
  override def allFoods = List(Apple, Orange, Cream, Sugar)
  
  override def allRecipes: List[Recipe] = List(FruitSalad)
  
  private var categories = List(
    FoodCategory("fruits", List(Apple, Orange)),
    FoodCategory("misc", List(Cream, Sugar)))
    
  override def allCategories = categories
}