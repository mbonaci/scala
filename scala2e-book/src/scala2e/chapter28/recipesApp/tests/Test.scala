package scala2e.chapter28

object Test {
  def main(args: Array[String]): Unit = {}
}

abstract class Database {
  def allFoods: List[Food]
  def allRecipes: List[Recipe]
  
  def foodNamed(name: String) =
    allFoods.find(f => f.name == name)
    
  case class FoodCategory(name: String, foods: List[Food])
  
  def allCategories: List[FoodCategory]
}

abstract class Browser {
  val database: Database
  
  def recipesUsing(food: Food) =
    database.allRecipes.filter(recipe =>
      recipe.ingredients.contains(food))

  def displayCategory(category: SimpleDatabase.FoodCategory) {
    println(category)
  }
}


object SimpleDatabase extends Database {
  override def allFoods = List(Apple, Orange, Cream, Sugar)
  
  override def allRecipes: List[Recipe] = List(FruitSalad)
  
  private var categories = List(
    FoodCategory("fruits", List(Apple, Orange)),
    FoodCategory("misc", List(Cream, Sugar)))
    
  override def allCategories = categories
}

object SimpleBrowser extends Browser {
  val database = SimpleDatabase
}


object Apple extends Food("Apple")
object Orange extends Food("Orange")
object Cream extends Food("Cream")
object Sugar extends Food("Sugar")
object FruitSalad extends Recipe(
  "fruit salad",
  List(Apple, Orange, Cream, Sugar),
  "Shake em up."
)