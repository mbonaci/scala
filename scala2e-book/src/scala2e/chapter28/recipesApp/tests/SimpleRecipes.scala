package scala2e.chapter28.recipesApp.tests

import scala2e.chapter28.recipesApp.domain.Recipe

trait SimpleRecipes {
  this: SimpleFoods =>  // self type
    // presents a requirement to a class that mixes this trait in
    // has to always be mixed in together with SimpleFoods
    
  object FruitSalad extends Recipe(
    "fruit salad",
    List(Apple, Pear), // Pear is in scope because of self type
    "Mix it all together."
  )
  def allRecipes = List(FruitSalad)
}