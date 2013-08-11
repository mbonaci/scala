package scala2e.chapter15

object WChapter15 {

  val exa = "a"                                   //> exa  : String = a
  val exb = "b"                                   //> exb  : String = b
  
  val res0 = exa match {
    case exb => println("this matched letter '" + exb + "'");
    case _ =>
      println("why is this allowed after variable pattern");
    case v =>
      println("why is this allowed after variable and wildcard patterns");
  }                                               //> this matched letter 'a'
                                                  //| res0  : Unit = ()
                                                  
                                                  
  val lst0 = List(2)                              //> lst0  : List[Int] = List(2)
  val lst1 = (lst0 :+ 2).drop(2)                  //> lst1  : List[Int] = List()
  
  
  val res1 = lst0 match {
    case List(0, _*) => println("starts with 0")
    case List(_*) => println("may be empty")
    
  }                                               //> may be empty
                                                  //| res1  : Unit = ()

  val lst2 = lst1 :+ 0                            //> lst2  : List[Int] = List(0)
  
  val res2 = lst2 match {
    case List(0, _*) => println("starts with 0")
    case List(_*) => println("may be empty")
    
  }                                               //> starts with 0
                                                  //| res2  : Unit = ()

  ("a ", 3, "-tuple") match {
    case (a, b, c) => println("matched " + a + b + c)
    case _ =>
  }                                               //> matched a 3-tuple
  
  
  def generalSize(x: Any) = x match {
    case s: String => s.length
    case m: Map[_, _] => m.size
    case _ => -1
  }                                               //> generalSize: (x: Any)Int

  generalSize("abcd")                             //> res0: Int = 4
  generalSize(Map(1 -> 'a', 2 -> 'b'))            //> res1: Int = 2
  generalSize(math.Pi)                            //> res2: Int = -1
  
  def isIntToIntMap(x: Any) = x match {
    case m: Map[Int, Int] => true
    case _ => false
  }                                               //> isIntToIntMap: (x: Any)Boolean

  isIntToIntMap(Map(1 -> 2, 2 -> 3))              //> res3: Boolean = true
  isIntToIntMap(Map("abc" -> "abc"))              //> res4: Boolean = true
  
  def isIntArray(x: Any) = x match {
    case m: Array[Int] => true
    case _ => false
  }                                               //> isIntArray: (x: Any)Boolean

  isIntArray(Array(1, 2))                         //> res5: Boolean = true
  isIntArray(Array("abc", "def"))                 //> res6: Boolean = false




  def simplifyAdd(e: Expr) = e match {
    case BinOp("+", x, y) if x == y =>
  BinOp("*", x, Number(2))
    case _ => e
  }                                               //> simplifyAdd: (e: scala2e.chapter15.Expr)scala2e.chapter15.Expr

  val add = BinOp("+", Number(24), Number(24))    //> add  : scala2e.chapter15.BinOp = BinOp(+,Number(24.0),Number(24.0))
  val timesTwo = simplifyAdd(add)                 //> timesTwo  : scala2e.chapter15.Expr = BinOp(*,Number(24.0),Number(2.0))



	def simplifyAll(expr: Expr): Expr = expr match {
	  case UnOp("-", UnOp("-", e)) =>
	    simplifyAll(e) // '-' is its own inverse
	  case BinOp("+", e, Number(0)) =>
	    simplifyAll(e) // '0' is a neutral element for '+'
	  case BinOp("*", e, Number(1)) =>
	    simplifyAll(e) // '1' is a neutral element for '*'
	  case UnOp(op, e) =>
	    UnOp(op, simplifyAll(e))
	  case BinOp(op, l, r) =>
	    BinOp(op, simplifyAll(l), simplifyAll(r))
	  case _ => expr
	}                                         //> simplifyAll: (expr: scala2e.chapter15.Expr)scala2e.chapter15.Expr
	
	implicit def intToNumber(x: Int) = new Number(x)
                                                  //> intToNumber: (x: Int)scala2e.chapter15.Number
	implicit def numberToInt(x: Number) = x.num.toInt
                                                  //> numberToInt: (x: scala2e.chapter15.Number)Int
	  
	val allMin = UnOp("-", UnOp("-", 4))      //> allMin  : scala2e.chapter15.UnOp = UnOp(-,UnOp(-,Number(4.0)))
	val allAdd = BinOp("+", 244, 0 + Number(0))
                                                  //> allAdd  : scala2e.chapter15.BinOp = BinOp(+,Number(244.0),Number(0.0))
	val allMul = BinOp("*", 24, 1)            //> allMul  : scala2e.chapter15.BinOp = BinOp(*,Number(24.0),Number(1.0))
	simplifyAll(allMin)                       //> res7: scala2e.chapter15.Expr = Number(4.0)
	simplifyAll(allAdd)                       //> res8: scala2e.chapter15.Expr = Number(244.0)
	simplifyAll(allMul)                       //> res9: scala2e.chapter15.Expr = Number(24.0)
	

}