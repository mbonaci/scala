package scala2e.chapter29.recipesApp.tests

import scala2e.chapter29.recipesApp.domain.Food

trait SimpleFoods {
  object Pear extends Food("Pear")
  def allFoods = List(Apple, Pear)
  def allCategories = Nil
}