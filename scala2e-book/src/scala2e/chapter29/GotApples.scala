package scala2e.chapter29

import scala2e.chapter29.recipesApp.application.{Database, Browser}
import scala2e.chapter29.recipesApp.tests.{SimpleDatabase, StudentDatabase}

// a simple program that chooses a database at runtime:
object GotApples {
  def main(args: Array[String]) {
    val db =
      if(args(0) == "student")
        StudentDatabase
      else
        SimpleDatabase

    object browser extends Browser {
      val database: db.type = db
    }

    val apple = db.foodNamed("Apple").get
    for (recipe <- browser.recipesUsing(apple))
      println(recipe)
      
    for (cat <- db.allCategories)
      browser.displayCategory(cat)
    
  }
}