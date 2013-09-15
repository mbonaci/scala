package scala2e.chapter28.recipesApp.application

import scala2e.chapter28.recipesApp.tests.SimpleDatabase
import scala2e.chapter28.recipesApp.domain.Food
import scala2e.chapter28.recipesApp.application.Database

abstract class Browser {
  val database: Database
  
  def recipesUsing(food: Food) =
    database.allRecipes.filter(recipe =>
      recipe.ingredients.contains(food))

  def displayCategory(category: SimpleDatabase.FoodCategory) {
    println(category)
  }
}