package scala2e.chapter28

// entity
class Recipe (
  val name: String,
  val ingredients: List[Food],
  val instructions: String
) {
  override def toString = name
}