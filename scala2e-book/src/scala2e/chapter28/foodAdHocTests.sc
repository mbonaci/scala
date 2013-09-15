package scala2e.chapter28

object foodAdHocTests {
  val apple = SimpleDatabase.foodNamed("Apple").get
                                                  //> apple  : scala2e.chapter28.Food = Apple
  SimpleBrowser.recipesUsing(apple)               //> res0: List[scala2e.chapter28.Recipe] = List(fruit salad)





}