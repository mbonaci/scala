package scala2e.chapter28.recipesApp.domain

// entity
abstract class Food(val name: String) {
  override def toString = name
}