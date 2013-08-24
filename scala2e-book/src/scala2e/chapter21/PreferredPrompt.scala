package scala2e.chapter21

object Prompt {
  def main(args: Array[String]): Unit = {
    val bobsPrompt = new PreferredPrompt("relax> ")
    val bobsDrink = new PreferredDrink("travarica")
    Greeter.greet("Bob")(bobsPrompt, bobsDrink)
    
    implicit val prompt = new PreferredPrompt("Yes, master> ")
    implicit val drink = new PreferredDrink("rakija")
    Greeter.greet("Joe")
  }
}

class PreferredPrompt(val preference: String)
class PreferredDrink(val preference: String)

object Greeter {
  def greet(name: String)(implicit prompt: PreferredPrompt, drink: PreferredDrink) {
    println("Welcome, " + name + ". The system is ready.")
    print("But while you work, ")
    println("why not enjoy a cup of " + drink.preference + "?")
    println(prompt.preference)
  }
}