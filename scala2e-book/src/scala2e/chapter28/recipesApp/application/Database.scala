package scala2e.chapter28.recipesApp.application

import scala2e.chapter28.recipesApp.domain.Food
import scala2e.chapter28.recipesApp.domain.Recipe
import scala2e.chapter28.recipesApp.domain.FoodCategories

abstract class Database extends FoodCategories {
  def allFoods: List[Food]
  def allRecipes: List[Recipe]
  
  def foodNamed(name: String) =
    allFoods.find(f => f.name == name)
}