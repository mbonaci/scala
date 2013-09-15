package scala2e.chapter29.recipesApp.application

import scala2e.chapter29.recipesApp.tests.SimpleDatabase
import scala2e.chapter29.recipesApp.domain.Food

abstract class Browser {
  val database: Database
  
  def recipesUsing(food: Food) =
    database.allRecipes.filter(recipe =>
      recipe.ingredients.contains(food))

  def displayCategory(category: SimpleDatabase.FoodCategory) {
    println(category)
  }
}