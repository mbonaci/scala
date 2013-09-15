package scala2e.chapter29.recipesApp.tests

import scala2e.chapter29.recipesApp.application.Browser
import scala2e.chapter29.recipesApp.application.Database
import scala2e.chapter29.recipesApp.domain.Food
import scala2e.chapter29.recipesApp.domain.Recipe

object StudentDatabase extends Database {
  object FrozenFood extends Food("FrozenFood")
  
  object HeatItUp extends Recipe(
    "heat it up",
    List(FrozenFood),
    "Microwave the 'food' for couple of minutes.")
  
  def allFoods = List(FrozenFood)
  def allRecipes = List(HeatItUp)
  def allCategories = List(
    FoodCategory("edible", List(FrozenFood)))
}
  
object StudentBrowser extends Browser {
  val database = StudentDatabase
}
