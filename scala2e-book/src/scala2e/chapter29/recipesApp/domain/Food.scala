package scala2e.chapter29.recipesApp.domain

// entity
abstract class Food(val name: String) {
  override def toString = name
}