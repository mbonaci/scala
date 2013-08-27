package scala2e.chapter23

object ForExpressionsRevisited {

  def main(args: Array[String]): Unit = {
    val leti = Person("Leti", false)
    val hela = Person("Hela", false, leti)
    val gaetano = Person("Gaetano", true, leti)
    val marko = Person("Marko", true)
    val branka = Person("Branka", false, hela, marko)
    val persons = List(leti, hela, gaetano, marko, branka)
    
    val zenke = persons filter (p => !p.isMale)
    val x = zenke map (p => p.children)
    val xf = zenke flatMap (p => p.children)
    val mothersWitChildren = persons withFilter (p => !p.isMale) flatMap (p =>
        (p.children map (c => (p.name, c.name))))
    
    println(zenke)
    println(x)
    println(xf)
    println(mothersWitChildren)
    
    val forList = for (p <- persons;
    	if !p.isMale;
    	c <- p.children) yield (p.name, c.name)
    
    println(forList)
  }
  
  case class Person(name: String,
		  			isMale: Boolean,
		  			children: Person*) {
    override def toString = name
  }


}