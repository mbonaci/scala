Java developer's Scala cheat sheet
-----

### 252 - Scala type hierarchy
![Scala class hierarchy image](http://i1329.photobucket.com/albums/w548/mbonaci/Scala-class-hierarchy_zps124c49e1.png)

### 117 Basic Types and Operations
* 127 - The convention is to include empty parentheses when invoking a method only if that method has side effects

> - **Pure methods** are methods that don't have any side effects and don't depend on mutable state (226) 
>   - if the function you're calling performs an operation, use the parentheses, but if it merely provides access to a property, leave out the parentheses

* 127 - **Postfix operator**: A method that takes no arguments can be called like this: `"some String" toLowerCase`
* 127 - Integral types: `Int`, `Long`, `Byte`, `Short`, `Char`
* 135 - Operator precedence:

>  `(all other special characters)` 
>  `*` `/` `%`  
>  `+` `-`  
>  `:`  
>  `=` `!`  
>  `<` `>`  
>  `&`  
>  `ˆ`   - binary xor  
>  `|`  
>  `(all letters)`  
>  `(all assignment operators)`  

* 136 - The **operator precedence** is based on the first character of the method used in operator notation, with one exception: If an operator ends with a `=`, and the operator is not one of the comparison operators `<=`, `>=`, `==`, or `!=`, then the precedence of the operator is the same as that of simple assignment `=`, which is last in the list. E.g. `+=`
* 136 - **Associativity**: any method that ends in a `:` character is invoked on its right operand, passing in the left operand. Methods that end in any other character are invoked on their left operand, passing in the right operand. So `a * b` yields `a.*(b)`, but `a ::: b` yields `b.:::(a)`
* 137 - `a ::: b ::: c` is treated as `a ::: (b ::: c)`

### 139 Functional Objects
* 141 - **Class parameters**: Any code placed in the class body (outside methods) will be placed in the *primary constructor*. When declaring a class you can drop empty `{}`
* 143 - A **precondition** is a constraint on values passed into a method or constructor (E.g. `require(d != 0)` in the class body will throw `IllegalArgumentException: requirement failed` when `0` is passed as `d`)
* 144 - If **Class parameters** are only used inside constructors, the Scala compiler will not create corresponding fields for them
* 146 - **Auxiliary constructors** - constructors other than the primary constructor

> - Every *auxiliary constructor* must invoke another constructor **of the same class** (like Java, only Java can also call superclass's constructor instead) as its first action. That other constructor must textually come before the calling constructor

* 152 - The convention is to use camel case for constants, such as `XOffset`
* 153 - The Scala compiler will internally “mangle” operator identifiers to turn them into legal Java identifiers with embedded `$` characters. For instance, the identifier `:->` would be represented internally as `$colon$minus$greater`. If you ever wanted to access this identifier from Java code, you'd need to use this internal representation
* 153 - A **mixed identifier** consists of an alphanumeric identifier, which is followed by an underscore and an operator identifier, e.g. `unary_+` (used to support *properties*)
* 153 - A **literal identifier** is an arbitrary string enclosed in back ticks
* 156 - **Implicit conversion** definition:

```scala
implicit def intToRational(x: Int) = new Rational(x)
```
for an implicit conversion to work, it needs to be in scope. If you place the implicit method definition inside the class Rational, it won't be in scope in the interpreter

### 159 Built-in Control Structures
* 163 - **Assignment** always results in the **unit value**, `()`
* 164 - In `for (file <- files)` the `<-` is called a **generator**. In each iteration, a new `val` named `file` is initialized with an element value
* 164 - The `Range` type: `4 to 8`. If you don't want upper bound: `4 until 8`
* 166 - **filter**: `for (file <- files if file.getName.endsWith(".scala"))`. Multiple filters example:

```scala
  for (
    file <- filesHere
    if file.isFile
    if file.getName.endsWith(".scala")
  ) println(file)
```

* 167 - **Nested loops** and **mid-stream variable binding** example with generators and filters. *Curly braces are used because the Scala compiler will not infer semicolons inside parentheses*

```scala
  def grep(pattern: String) =
    for {
      file <- files if file.getName.endsWith(".scala")  // semicolons inferred
      line <- fileLines(file)
      trimmed = line.trim  // mid-stream variable
      if trimmed.matches(pattern)
    } println(file + ": " + trimmed)
    
```

* 168 - **yield** keyword makes `for` clauses produce a value (of the same type as the expression iterated over). Syntax: `for` *clauses* `yield` *body* 
* 174 - **match case** example:

```scala
val target = firstArg match {  // firstArg is a previously initialized val
  case "salt" => println("pepper")
  case "chips" => println("salsa")
  case "eggs" => println("bacon")
  case _ => println("huh?")
}
```

Unlike Java's `select case`, there is no fall through, `break` is implicit. `case` expression can contain any type of value
`_` is a placeholder for *completely unknown value*

* 175 - In Scala, there's no `break` nor `continue` statements
* 180 - Unlike Java, Scala supports *inner scope variable shadowing*

### 184 Functions and Closures
* 186 - **Local functions** are functions inside other functions. They are visible only in their enclosing block
* 188 - **Function literal** example: `(x: Int) => x + 1`
* 188 - Every function value is an instance of some class that extends one of `FunctionN` traits that has an `apply` method used to invoke the function (`Function0` for functions with no params, `Function1` for functions with 1 param, ...)
* 189 - **foreach** is a method of `Traversable` trait (supertrait of `List`, `Set`, `Array` and `Map`) which takes a function as an argument and applies it on all elements
* 190 - **filter** method takes a function that maps each element to true or false, e.g. `someNums.filter((x: Int) => x > 0)`
* 190 - **Target typing** - Scala infers type by examining the way the expression is used, e.g. `filter` example can be written: `someNums.filter(x => x > 0)`
* 191 - **Placeholder** allow you to write: `someNums.filter(_ > 0)`, but only if each function parameter appears in function literal only once (one underscore for each param, sequentially).
Sometimes the compiler might not have enough info to infer missing param types:

```scala
val f = _ + _  // error: missing parameter type for expanded function...
val f = (_: Int) + (_: Int)  // OK: f(5, 10) = 15
```

* 192 - **Partially applied function (PAF)** is an expression in which you don't supply all of the arguments needed by the function. Instead, you supply some, or none:

```scala
someNums.foreach(println _)  
// is equivalent to:
someNums.foreach(x => println(x))
// if a function value is required in that place you can omit the placeholder:
someNums.foreach(println)
```

```scala
scala> def sum(a: Int, b: Int, c: Int) = a + b + c

scala> val a = sum _  // '_' is a placeholder for the entire param list
a: (Int, Int, Int) => Int = <function3>

// They are called partially applied functions because you can do this:
scala> val b = sum(1, _: Int, 3)
scala> b(2)
res0: Int = 6
```

* 197 - **Closures** see the changes to **free variables** and vice versa, changes to the *free variable* made by *closure* are seen outside of *closure*
* 199 - **Repeated parameters** Scala allows you to indicate that the last param to a function may be repeated. Syntax: `def echo(args: String*) = for(arg <- args) println(arg)`. Now `echo` may be called with zero or more params.  
To pass in an `Array[String]` instead of `String*` you need to append the arg with a colon and an `_*` symbol: `echo(arr: _*)`
* 200 - **Named arguments** allow you to pass args to a function in a different order. The syntax is to precede each argument with a param name and an equals sign: `speed(distance = 100, time = 10)`. It is also possible to mix positional and named args, in which case the positional arguments, understandably, must come first
* 201 - **Default parameter values** allows you to omit such a param when calling a function, in which case the param will be filled with its default value:

```scala
def printTime(out: java.io.PrintStream = Console.out) = 
  out.println("time = " +  System.currentTimeMillis())

// Now, you can call the function like this: 
printTime()
// or like this: 
printTime(Console.err)
```

* 202 - **Tail recursion** (**Tail call optimization**) If the recursive call is the last action in the function body, compiler is able to replace the call with a jump back to the beginning of the function, after updating param values.  

> - Because of the JVM instruction set, tail call optimization cannot be applied for two mutually recursive functions nor if the final call goes to a function value (function wraps the recursive call):

```scala
val funValue = nestedFun _
def nestedFun(x: Int) {
  if (x != 0) {println(x); funValue(x - 1)}  // won't be optimized
}
```

### 207 Control Abstractions
* 207 - **Higher order functions** - functions that take functions as params:

```scala
/** 
 * refactoring imperative code:
 * demonstrates control abstraction (higher order function)
 * that reduces code duplication and significantly simplifies the code
 *
 * the function receives a String and a function that maps (String, String) => Boolean
*/
def filesMatching(query: String, matcher: (String, String) => Boolean) = {
  for (
    file <- filesHere;  // filesHere is a function that returns Array of files
    if matcher(file.getName, query)
  ) yield file
}

def filesEnding(query: String) =
  filesMatching(query, (fileName: String, query: String) => fileName.endsWith(query))

def filesContaining(query: String) =
  filesMatching(query, (fileName, query) => fileName.contains(query)) // OK to omit types

def filesRegex(query: String) =
  filesMatching(query, _.matches(_))  // since each param is used only once


// Since the query is unnecessarily passed around,
// we can further simplify the code by introducing a closure
def filesMatching(matcher: String => Boolean) = {
  for(
    file <- filesHere;
    if matcher(file.getName)
  ) yield file
}
    
def filesRegex(query: String) =
  filesMatching(_.matches(query))  // 'matches' closes over free variable 'query'
```

* 213 - **Currying**: A curried function is applied to multiple argument lists, instead of just one:

```scala
scala> def curriedSum(x: Int)(y: Int) = x + y
curriedSum: (x: Int)(y: Int)Int

scala> curriedSum(1)(2)
res0 Int = 3

/*
Curried fn produces two traditional function invocations. The first function invocation
takes a single 'Int' parameter named 'x', and returns a function value for the second
function, which takes the 'Int' parameter 'y'
*/
// This is what the first function actually does:
scala> def first(x: Int) = (y: Int) => x + y  // returns function value
first: (x: Int)Int => Int

scala> val second = first(1)  // applying 1 to the first fn yields the second fn
first: (x: Int)Int => Int

scala> second(2)  // applying 2 to the second fn yields the final result
res1: Int = 3

/*
You can use the placeholder notation to use curriedSum in a partially applied function
expression which returns the second function:
*/
scala> val onePlus = curriedSum(1)_  // '_' is a placeholder for the second param list
onePlus: (Int) => Int = <function1>  // 'onePlus' does the same thing as 'second'
/*
when using placeholder notation with Scala identifiers you need to put a space between
identifier and underscore, which is why we didn't need space in 'curriedSum(1)_' and we
did need space for 'println _'
*/
```

* 215 - Another example of higher order function. `twice` repeats an operation two times and returns the result:

```scala
scala> def twice(op: Double => Double, x: Double) = op(op(x))
scala> twice(_ + 1, 5)  // f(f(x)) = x + 1 + 1, where x = 5
res2: Double = 7.0
```

* 216 - When some control abstraction function, such as `withPrintWriter` bellow, opens a resource and *loans* it to a function, we call that the **Loan pattern**:

```scala
def withPrintWriter(file: File, op: PrintWriter => Unit) {
  val writer = new PrintWriter(file)
  try {
    op(writer)  // loan the resource to the 'op' function
  } finally {   // this way we're sure that the resource is closed in the end
    writer.close()
  }
}
// to call the method:
withPrintWriter(
  new File("date.txt"),
  writer => writer.println(new java.util.Date)
)
```
In any method invocation in which you're passing in 'exactly one argument', you can opt to use curly braces instead of parentheses to surround the argument
Using *currying*, you can redefine `withPrintWriter` signature like this:

```scala
def withPrintWriter(file: File)(op: PrintWriter => Unit)
```
which now enables you to call the function with a more pleasing syntax:

```scala
val file = new File("date.txt")
withPrintWriter(file) { // this curly brace is the second parameter
    writer => writer.println(new java.util.Date)
}
```

* 218 - **By-name parameters** Typically, parameters to functions are *by-value* parameters, meaning, the value of the parameter is determined before it is passed to the function. What if you need to write a function that accepts as a parameter an expression that you don't want evaluated until it's called within your function? For this circumstance, Scala offers **call-by-name parameters**. A *call-by-name* mechanism passes a code block to the callee and each time the callee accesses the parameter, the code block is executed and the value is calculated:

```scala
var assertionsEnabled = true
def myAssert(predicate: () => Boolean) =  // without by-name parameter
  if (assertionsEnabled && !predicate())  // call it like this: myAssert(() => 5 > 3)
    throw new AssertionError

// To make a by-name parameter, you give the parameter a type
// starting with '=>' instead of '() =>'
def myAssert(predicate: => Boolean) =     // with by-name parameter
  if (assertionsEnabled && !predicate())  // call it like this: myAssert(5 > 3)
    throw new AssertionError              // which looks exactly like built-in structure

// You could use a plain-old Boolean, but then the passed expression would get executed
// before the call to 'boolAssert'
```

### 222 Composition and Inheritance
* 222 - **Composition** means one class holds a reference to another
* 224 - A method is **abstract** if it does not have an implementation (i.e., no equals sign or body). Unlike Java, no abstract modifier is allowed on method declarations. Methods that do have an implementation are called **concrete**.
* 224 - Class is said to **declare an abstract method** and that it **defines a concrete method** (i.e. *declaration* is *abstract*, *definition* is *concrete*)
* 225 - Methods with empty parentheses are called **empty-paren methods**. This convention (see bullet 127 on top) supports the *uniform access principle*, which says that the client code should not be affected by a decision to implement an attribute as a field or as a method (from the client code perspective, it should be irrelevant whether `val` or `def` is accessed. The only difference is speed, since fields are precomputed when the class is initialized)
* 229 - Fields and methods belong to the same *namespace*, which makes possible for a
field to override a parameterless method, but it forbids defining a field and a method with the same name 
* 230 - *Java* has four namespaces: fields, methods, types and packages
        *Scala* has two namespaces:
          **values** (fields, methods, packages and singleton objects)
          **types** (classes and traits)
* 231 - **Parametric field** is a shorthand definition for *parameter* and *field*, where *field* gets assigned a *parameter's* value (the parametric field's name mustn't clash with an existing element in the same namespace, like field or method):

```scala
class ArrayElement(
  val contents: Array[String]  // could be: 'var', 'private', 'protected', 'override'
)
```

* 232 - You pass an argument to the superconstructor by placing it in parentheses following the name of the superclass:

```scala
class LineElement(s: String) extends ArrayElement(Array(s)) {
  override def width = s.length  // 'override' mandatory for overrides of concrete members
  override def height = 1
}
```

* 238 - If you want to disallow for a method to be overridden or for a class to be subclassed, use the keyword **final** (e.g. `final class ...` or `final def ...`)
* 240 - **++** operator is used to concatenate two arrays
* 241 - **zip** is used to pair two arrays (make `Tuple2`s), dropping the elements from the longer array that don't have corresponding elements in the shorter array, so:

```scala
Array(1, 2, 3) zip Array("a", "b") // will evaluate to
Array((1, "a"), (2, "b"))

// 'zip' usage example
def beside(that: Element): Element =
  new ArrayElement(
    for(
      (line1, line2) <- this.contents zip that.contents  // new Tuple2 for each iteration
    ) yield line1 + line2
  )
```

* 242 - **mkString** is defined for all sequences (including arrays). `toString` is called on each element of the sequence. Separator is inserted between every two elems:

```scala
override def toString = contents mkString "\n"
```

### 250 Scala's Hierarchy
* 250 - In Scala hierarchy, **scala.Null** and **scala.Nothing** are the subclasses of every class (thus the name **bottom classes**), just as **Any** is the superclass of every other class
* 250 - `Any` contains methods:

> `==`..........`final`, same as `equals` (except for Java boxed numeric types)  
> `!=`..........`final`, same as `!equals`  
> `equals`....used by the subclasses to override equality  
> `##`...........same as `hashCode`  
> `hashCode`  
> `toString`  

* 251 - Class `Any` has two subclasses:

> `AnyVal`      the parent class of every built-in **value class** in Scala  
> `AnyRef`      the base class of all **reference classes** in Scala
> 
> Built-in **value classes**: `Byte`, `Short`, `Char`, `Int`, `Long`, `Float`, `Double`, `Boolean` and `Unit`
>  - represented (except `Unit`) as Java primitives at runtime
>  - both `abstract` and `final`, so you cannot instantiate them with `new`
>  - the instances of these classes are all written as literals (e.g. `5` is `Int`) 
>  - `Unit` corresponds to Java's `void` and has a single instance value, `()`
>  - Implicit conversion from `Int` to `RichInt` happens when a method that only exists in `RichInt` is called on `Int`. Similar **Booster classes** exist for other value types
>  
> All **reference classes** inherit from a special marker trait called `ScalaObject`

* 254 - Scala stores integers the same way as Java, as 32-bit words, but it uses the *backup* class `java.lang.Integer` to be used whenever an int has to be seen as object
* 256 - For **reference equality**, `AnyRef` class has `eq` method, which cannot be overridden (behaves like `==` in Java for reference types). Opposite of `eq` is `ne`
* 256 - *Null* is a subclass of every reference class (i.e. class that inherits from `AnyRef`). It's not compatible with *value types* (`val i: Int = Null // type mismatch`)
* *Nothing* is a subtype of every other type (of *Null* also). There are no values of this type, it's used primarily to signal abnormal termination:

```scala
def error(message: String): Nothing =
  throw new RuntimeException(message)

// because of its position in type hierarchy, you can use it like this:
def divide(x: Int, y: Int): Int =     // must return 'Int'
  if(y != 0) x / y                    // Returns 'Int'
  else error("can't divide by zero")  // 'Nothing' is a subtype of 'Int'
```

### 258 Traits
* 258 - **Trait** encapsulates method and field definitions, which can then be reused by mixing them into classes

> - *Trait* can be mixed in using keywords `extends` or `with`. The difference is that, by using `extends`, you implicitly inherit the trait's superclass (`AnyRef` if a trait has no explicit superclass)
> - *Trait* also defines a type which can be used as a regular class
> - If you want to mix a trait into a class that explicitly extends a superclass, use `extends` to indicate the superclass and `with` to mix in the trait:
> - To mix in multiple traits using `with`:
> - A class can override trait's members (polymorphism works the same way as with regular classes):

```scala
class Animal
class Frog extends Animal with Philosophical with HasLegs {
  override def toString = "green"
  override def philosophize() {
    println("It ain't easy being " + toString)
  }
}
```

* 261 - *Traits* can declare fields and maintain state (unlike Java interfaces). You can do anything in a trait definition that you can do with a class definition, with two exceptions:

> - traits cannot have *class parameters*
> - traits have dynamically bound `super` (unlike statically bound `super` in classes)
>   - the implementation to invoke is determined each time the trait is mixed into class
>   - key to allowing traits to work as *stackable modifications*

* 266 - **Ordered trait** allows you to implement all comparison operations on a class

> - requires you to specify a *type parameter* when you mix it in (`extends Ordered[TypeYouCompare]`)
> - requires you to implement the `compare` method, which should return `Int`, `0` if the object are the same, negative if receiver is less than the argument and positive if the receiver is greater than the argument
> - does not provide `equals` (because of "type erasure")

* 267 - **Stackable modifications**

> - traits let you modify the methods of a class in a way that allows you to stack those modifications together, by mixing in multiple traits
> - when a trait extends a superclass, it means that the trait can only be mixed in in classes that also extend the same superclass
> - traits can have `abstract override` methods because of dynamically bound `super` (the call works if the trait is mixed in after another trait or class has already given a concrete definition to that method)
> - when you instantiate a class with `new` Scala takes the class and all of its inherited classes and traits and puts them in a single, *linear* order, thus this behavior is called **linearization**. Then, when you call `super` inside one of those classes, the invoked method is the first implementation up the chain (right in the image)
> - the **order of mixins** is significant. Traits further to the right take effect first

```scala
# // mixing in a trait when instantiating with 'new' (no need to create a new class)
scala> val queue = new BasicIntQueue with Doubling with Filtering  // filtering is applied first
queue: BasicIntQueue with Doubling with Filtering = $anon$1@5fa12d

scala> queue.put(10)  // passes the Filtering and then gets doubled with Doubling trait
scala> queue.put(-1)  // not placed in the queue (negative number filter trait applied)
scala> queue.get()
res14: Int = 20
```

![Scala Linearization](http://i1329.photobucket.com/albums/w548/mbonaci/Linearization-demonstration-image_zps07e8485d.jpg)
![Linearization order](http://i1329.photobucket.com/albums/w548/mbonaci/Linearization-order-image_zps229e88da.jpg)

* 275 - **When to use a _trait_ and when an _abstract class_**

> - if the behavior will not be reused make a concrete class
> - if it might be used in multiple, unrelated classes, use a trait
> - if you want to inherit from it in Java code, use an abstract class
>   - a trait with only abstract members translates to Java `interface`
> - if you plan to distribute it in compiled form and you expect others to write classes that inherit from your code, use an abstract class (when a trait gains or loses a member, any class that inherit from it must be recompiled)
> - if efficiency is very, very important, use a class (in Java, a virtual method invocation of a class member is faster than an interface method invocation)
> - if none of the above fits your case, use trait

### 277 Packages and Imports
* 278 - **Packages** can be used like in C#: `package pkg_name { // source... }`, with more packages in a single source file. Also, they can be nested in one another

> - a package represents a scope, whose contents is accessed relative to current location
> - a top level package that's outside all packages user can write is called `_root_`
> - all names accessible outside packages can be access from inside the package the same way
> - if you stick with one package per file then Java package rules apply

```scala
// this
package one
package two
// is syntactic sugar for this
package one {
  package two {

// to import the package (not a specific package member)
import one.two  // and then use objects like this: `two.Two.method`

// which is a shorthand for 'Import selector' syntax:
import one.{two}

// to access all members of a package (underscore instead of Java's star)
import one.two.three._  // could also be written as `import one.two.three.{_}`

// to use import with objects and classes
def showOne(one: One) {  // imports all members of its parameter `one` of class `One`
  import one._  // use imports wherever you like
  println(name + "s are the best")
}

// to import more than one specific package member use 'Import selectors'
import one.{One1, One2}

// to rename import
import one.{One1 => First, One2}  // `One1` is accessed as `First` (or `one.One1`)

// to import all members and rename one of them
import one.two.{Two => Second, _}  // catch-all must come last in the list

// to import all members except one (useful for ambiguities)
import one.two.{Two => _, _}  // excludes `Two`

// implicit imports (later imports overshadow earlier ones)
import java.lang._
import scala._
import Predef._
```

* 288 - **Access modifiers** available in Scala: `Private` and `Protected`

> - outer class's access to `private` members of its inner class is forbidden
> - Java allows access to `protected` members to classes in the same package even if they don't inherit from the class that declares protected members. Scala don't

* 289 - **Access qualifiers**

> - a modifier in the form `private[X]` or `protected[X]` means that access is applied "up to X", where `X` designates some enclosing package, class or a singleton
> - **object-private** `private[this]` means that access is allowed only from within the the object that contains definition itself, not its instances (`ObjName.privMember` will fail in this case)

* 291 - **Companion objects** or **Singletons**

> - a class shares all its access rights with its companion object and vice versa
> - `protected` modifier makes no sense since *Companion objects* cannot be subclassed

* 292 - **Package objects**

> - any kind of definition you can put in a class can go in a *package object*
> - each package is allowed to have one *package object*
> - frequently used to hold package-wide *type aliases* and *implicit conversions*
> - the top level `scala` package has a package object, which is available to all Scala code
> - they are compiled to `package.class` file in that package's directory
> - access is the same as for any other package element:

```scala
// in file 'one/package.scala'
package object one {
  def showSomeone(someone: Someone) {
    import someone._
    println(name + ", I am")
  }
}
// in file View.scala
package view
import one.Someone  // class defined in package 'one'
import one.showSomeone
object ViewDialog {
  def main(args: Array[String]) {
    for(someone <- Someone.dialog) {
      showSomeone(someone)
    }
  }
}
```

### 295 Assertions and Unit Testing
* 295 - **Assertions**

> - written as calls of a predefined method `assert` (defined in the `Predef` singleton)
> - assertions and ensuring checks can be enabled/disabled with JVM's `-ea`/`-da` flags
> - `assert` methods and `ensuring` convenience methods:

```scala
assert(condition) // throws AssertionError
assert(condition, explanation: Any) // AssertionError contains explanation.toString

// 'ensuring' example
def widen(w: Int): Element =
  if(w <= width) this
  else {
    val left = elem(' ', (w - width) / 2, height)
    val right = elem(' ', w - width - left.width, height)
    left beside this beside right
  } ensuring(w <= _.width)  // takes a predicate function
                            // when invoked, it passes return type ('Element') to the 
                            // predicate function that returns 'Boolean'
// if predicate evaluates to 'true', 'ensuring' results with 'Element' on which it was invoked
// since this is the last expression of the method, 'widen' returns the 'Element'
// throws AssertionError if predicate returns 'false'
```

* 297 - **Unit testing**

> - there are many options for unit testing in Scala, e.g. Java `JUnit` and `TestNG` tools or tools written in Scala, e.g. `ScalaTest`, `specs` and `ScalaCheck`  

> - [ScalaTest](http://www.scalatest.org)
>   - the simplest way to test with *ScalaTest* is to extend `org.scalatest.Suite` and define test methods in those classes. Methods start with `test`:

```scala
import org.scalatest.Suite
import Element.elem

class ElementSuite extends Suite {
  def testUniformElement() {
    val e = elem('x', 2, 3)
    assert(e.width == 2)
  }
}

// ScalaTest offers a trait 'FunSuite', which overrides 'execute'
// so you can define tests as function values, rather than methods
class ElementSuite extends FunSuite {
  // test is a method in FunSuite which is invoked by ElementSuite's primary constructor
  test("elem result should have passed width") {  // name of test
    // curly - function passed as by-name param to 'test', which registers it for later execution
    val e = elem('x', 2, 3)
    assert(e.width == 2)  // if fails you see error message with line number
  }
}

// triple equals, if assert fails, returns nice error msg. e.g. "3 did not equal 2":
assert(e.width === 2)

// alternatively, 'expect' can be used:
expect(2) {  // yields "expected 2, but got 3" in the test failure report
  e.width
}

// if you want to check whether a method throws expected exception use 'intercept'
// if the code does not throw expected exception or doesn't throw at all
// 'TestFailedException' is thrown, along with a helpful error msg
intercept[IllegalArgumentException] {  // returns caught exception
  elem('x', -2, 3)
}
```

>   - although ScalaTest includes Runner application, you can also run Suite directly from the Scala interpreter by invoking `execute` on it (trait Suite's `execute` method uses reflection to discover its test methods and invoke them):

```scala
scala> (new ElementSuite).execute()
Test Starting - ElementSuite.testUniformElement
Test Succeeded - ElementSuite.testUniformElement
```

>   - in **BDD**, the emphasis is on writing human-readable specifications of the expected code behavior, along with the accompanying tests that verify that behavior
>   - for that purpose, ScalaTest includes several traits: Spec, WordSpec, FlatSpec and FeatureSpec

```scala
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import Element.elem

class ElementSpec extends FlatSpec with ShouldMatchers {
  "A UniformElement" should "have a width equal to the passed value" in {
    val e = elem('x', 2, 3)
    e.width should be (2)
  }
  it should "have a height equal to the passed value" in {  // 'specifier clause'
    val e = elem('x', 2, 3)
    e.height should be (3)
  }
  it should "throw an IAE if passed a negative width" in {  // or 'must' or 'can'
    evaluating {
      elem('x', -2, 3)
    } should produce [IllegalArgumentException]
  }
}
```

### 309 Case Classes and Pattern Matching
* 310 - **Case classes**

> - for classes with `case` modifier, Scala compiler adds some syntactic sugar:
>   - a factory method with the same name as the class, which allows you to create new object without keyword `new` (`val m = MyCls("x")`)
>   - all class parameters implicitly get a `val` prefix, so they are made into fields
>   - compiler adds "natural" implementations of methods `toString`, `hashCode` and `equals`, which will print, hash and compare a whole tree of the class and its arguments
>   - `copy` method is added to the class (used to create modified copies). To use it, you specify the changes by using *named parameters* and for any param you don't specify, the original value is used:

```scala
// copy method example
scala> abstract class Expr
scala> case class Var(name: String) extends Expr
scala> case class Number(num: Double) extends Expr
scala> case class UnOp(operator: String, arg: Expr) extends Expr
scala> case class BinOp(operator: String, left: Expr, right: Expr) extends Expr
scala> val op = BinOp("+", Number(1), Var("x"))
op: BinOp = BinOp(+,Number(1.0),Var(x))

scala> op.copy(operator = "-")
res0: BinOp = BinOp(-,Number(1.0),Var(x))
```

* 312 - **Pattern matching**

> - the biggest advantage of *case classes* is that they support *pattern matching*

```scala
// written in the form of 'selector match {alternatives}'
def simplifyTop(expr: Expr): Expr = expr match {
  case UnOp("-", UnOp("-", e))  => e
  case BinOp("+", e, Number(0)) => e
  case BinOp("*", e, Number(1)) => e
  case _ => expr
}
```

> - `match` expression is evaluated by trying each of the patterns in the order they are written. The first pattern that matches is selected and the part following the fat arrow is executed
> - a **constant pattern** like `"+"` or `1` matches values that are equal to the constant when compared with `==`
> - a **variable pattern** like `e` matches every value and variable than refers to that value in the right hand side of the case clause
> - the **wildcard pattern** `_` matches every value, but it doesn't result with variable
> - a **constructor pattern** `UnOp("-", e)` matches any value of type `UnOp` whose first argument matches `"-"` and second argument matches `e`. Argument to the constructor is itself a pattern (pattern nesting)
> - `match` _is an expression_ in Scala (always results in a value)
> - there is _no *fall through*_ behavior into the next case
> - _if none of the patterns match_, an exception `MatchError` is thrown