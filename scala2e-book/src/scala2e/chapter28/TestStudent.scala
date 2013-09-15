package scala2e.chapter28

object TestStudent{
  def main(args: Array[String]): Unit = {}
}

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
