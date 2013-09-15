package scala2e.chapter29.recipesApp.domain

trait FoodCategories {
  case class FoodCategory(name: String, foods: List[Food])
  
  def allCategories: List[FoodCategory]
}