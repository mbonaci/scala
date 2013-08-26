Java developer's Scala cheat sheet
-----
Shamelessly ripped of from [Programming in Scala, second edition](http://www.artima.com/shop/programming_in_scala_2ed).
(I did ask for permission).  
Basically, while I'm going through the book, I'm taking notes here, so I can later use it as a quick reference.
If you, by any incredible chance, find any of this useful, please do buy the book (no, I don't get the kick back. As you can see, the book link is clean).  

### Scala type hierarchy
![Scala class hierarchy image](https://github.com/mbonaci/scala/blob/master/resources/Scala-class-hierarchy.png?raw=true)

### Basic Types and Operations
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
* 137 - `a ::: b ::: c` is treated as `a ::: (b ::: c)` (list concatenation)

### Functional Objects
* 141 - **Class parameters**: Any code placed in the class body (outside methods) will be placed in the *primary constructor*. When declaring a class you can drop empty `{}`
* 143 - A **precondition** is a constraint on values passed into a method or constructor (E.g. `require(d != 0)` in the class body will throw `IllegalArgumentException: requirement failed` when `0` is passed as `d`)
* 144 - If **Class parameters** are only used inside constructors, the Scala compiler will not create corresponding fields for them
* 146 - **Auxiliary constructors** - constructors other than the primary constructor

> - every *auxiliary constructor* must invoke another constructor **of the same class** (like Java, only Java can also call superclass's constructor instead) as its first action. That other constructor must textually come before the calling constructor

* 152 - The convention is to use camel case for constants, such as `XOffset`
* 153 - The Scala compiler will internally “mangle” operator identifiers to turn them into legal Java identifiers with embedded `$` characters. For instance, the identifier `:->` would be represented internally as `$colon$minus$greater`. If you ever wanted to access this identifier from Java code, you'd need to use this internal representation
* 153 - A **mixed identifier** consists of an alphanumeric identifier, which is followed by an underscore and an operator identifier, e.g. `unary_+` (used to support *properties*)
* 153 - A **literal identifier** is an arbitrary string enclosed in back ticks. Used to tell Scala to treat a keyword as an ordinary identifier, e.g., writing `Thread.'yield'()` treats `yield` as an identifier rather than a keyword.
* 156 - **Implicit conversion** definition:

```scala
implicit def intToRational(x: Int) = new Rational(x)
```
> - for an implicit conversion to work, it needs to be in scope. If you place the implicit method definition inside the class `Rational`, it won't be in scope

### Built-in Control Structures
* 163 - **Assignment** always results with the **unit value**, `()`
* 164 - In `for (file <- files)` the `<-` is called a **generator**. In each iteration, a new `val` named `file` is initialized with an element value
* 164 - The `Range` type: `4 to 8`. If you don't want upper bound: `4 until 8`
* 166 - **filter**: `for (file <- files if file.getName.endsWith(".scala"))`

```scala
// multiple filters example:
for (
  file <- files  // files is a previously defined method that returns array of files
  if file.isFile
  if file.getName.endsWith(".scala")
) println(file)
```

* 167 - **Nested loops** and **mid-stream variable binding** example with _generators_ and _filters_

```scala
// curly braces are used because the Scala compiler will not infer semicolons inside parentheses
def grep(pattern: String) =
  for {
    file <- files if file.getName.endsWith(".scala")  // semicolons inferred
    line <- fileLines(file)
    trimmed = line.trim  // mid-stream variable
    if trimmed.matches(pattern)
  } println(file + ": " + trimmed)
```

* 168 - **yield** keyword makes `for` clauses produce a value (of the same type as the expression iterated over). Syntax: `for` *clauses* `yield` *body* 
* 174 - **match case**

> - unlike Java's `select case`, there is no fall through, `break` is implicit and `case` expression can contain any type of value
> - `_` is a placeholder for *completely unknown value*

```scala
val target = firstArg match {  // firstArg is a previously initialized val
  case "salt" => println("pepper")
  case "chips" => println("salsa")
  case "eggs" => println("bacon")
  case _ => println("waat?")
}
```

* 175 - In Scala, there's no `break` nor `continue` statements
* 180 - Unlike Java, Scala supports *inner scope variable shadowing*

### Functions and Closures
* 186 - **Local functions** are functions inside other functions. They are visible only in their enclosing block
* 188 - **Function literal** example: `(x: Int) => x + 1`
* 188 - Every function value is an instance of some class that extends one of `FunctionN` traits that has an `apply` method used to invoke the function (`Function0` for functions with no params, `Function1` for functions with 1 param, ...)
* 189 - **foreach** is a method of `Traversable` trait (supertrait of `List`, `Set`, `Array` and `Map`) which takes a function as an argument and applies it to all elements
* 190 - **filter** method takes a function that maps each element to true or false, e.g. `someNums.filter((x: Int) => x > 0)`
* 190 - **Target typing** - Scala infers type by examining the way the expression is used, e.g. `filter` example can be written: `someNums.filter(x => x > 0)`
* 191 - **Placeholder** allows you to write: `someNums.filter(_ > 0)`

> - only if each function parameter appears in function literal only once (one placeholder for each param, sequentially)
> - sometimes the compiler might not have enough info to infer missing param types:

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

// they are called partially applied functions because you can do this:
scala> val b = sum(1, _: Int, 3)
scala> b(2)  // Int = 6
```

* 197 - **Closures** see the changes to **free variables** and _vice versa_, changes to *free variables* made by *closure* are seen outside of *closure*
* 199 - **Repeated parameters** Scala allows you to indicate that the last param to a function may be repeated:

```scala
def echo(args: String*) = for(arg <- args) println(arg)
// Now `echo` may be called with zero or more params

// to pass in an `Array[String]` instead, you need to
// append the arg with a colon and an `_*` symbol:
echo(Array("arr", "of", "strings"): _*)
```

* 200 - **Named arguments** allow you to pass args to a function in a different order:

```scala
// The syntax is to precede each argument with a param name and an equals sign:
speed(distance = 100, time = 10)

// it is also possible to mix positional and named args
// in which case the positional arguments, understandably, must come first
```

* 201 - **Default parameter values** allows you to omit such a param when calling a function, in which case the param will be filled with its default value:

```scala
def printTime(out: java.io.PrintStream = Console.out) = 
  out.println("time = " +  System.currentTimeMillis())

// now, you can call the function like this: 
printTime()
// or like this: 
printTime(Console.err)
```

* 202 - **Tail recursion (Tail call optimization)**

> - if the recursive call is the last action in the function body, compiler is able to replace the call with a jump back to the beginning of the function, after updating param values
> - because of the JVM instruction set, tail call optimization cannot be applied for two mutually recursive functions nor if the final call goes to a function value (function wraps the recursive call):

```scala
val funValue = nestedFun _
def nestedFun(x: Int) {
  if (x != 0) {println(x); funValue(x - 1)}  // won't be optimized
}
```

### Control Abstractions
* 207 - **Higher order functions** - functions that take other functions as parameters:

```scala
/** 
 * refactoring imperative code:
 * demonstrates control abstraction (higher order function)
 * that reduces code duplication and significantly simplifies the code
*/
// function receives a String and a function that maps (String, String) => Boolean
def filesMatching(query: String, matcher: (String, String) => Boolean) = {
  for (
    file <- filesHere;  // filesHere is a function that returns an Array of files
    if matcher(file.getName, query)
  ) yield file
}

def filesEnding(query: String) =
  filesMatching(query, (fileName: String, query: String) => fileName.endsWith(query))

def filesContaining(query: String) =
  filesMatching(query, (fileName, query) => fileName.contains(query)) // OK to omit types

def filesRegex(query: String) =
  filesMatching(query, _.matches(_))  // since each 'matcher' param is used only once


// since the query is unnecessarily passed around,
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
// curriedSum: (x: Int)(y: Int)Int

scala> curriedSum(1)(2)
// Int = 3

/*
 * Curried f produces two traditional function invocations. The first function invocation
 * takes a single 'Int' parameter named 'x', and returns a function value for the second
 * function, which takes the 'Int' parameter 'y'
*/

// This is what the first function actually does:
scala> def first(x: Int) = (y: Int) => x + y  // returns function value
// (x: Int)Int => Int

scala> val second = first(1)  // applying 1 to the first fn yields the second fn
// (x: Int)Int => Int

scala> second(2)  // applying 2 to the second fn yields the final result
// Int = 3

/*
 * You can use the placeholder notation to use curriedSum in a partially applied function
 * expression which returns the second function:
*/
val onePlus = curriedSum(1)_  // '_' is a placeholder for the second param list
// onePlus: (Int) => Int = <function1>  // 'onePlus' does the same thing as 'second'

/*
when using placeholder notation with Scala identifiers you need to put a space between
identifier and underscore, which is why we didn't need space in 'curriedSum(1)_' and we
did need space for 'println _'
*/

// another example of higher order function, that repeats an operation two times
// and returns the result:
def twice(op: Double => Double, x: Double) = op(op(x))
twice(_ + 1, 5)  // f(f(x)) = x + 1 + 1, where x = 5
// Double = 7.0
```

* 216 - **Loan pattern**

> - some control abstraction function opens a resource and *loans* it to a function:

```scala
// opening a resource and loaning it to 'op'
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

/*
 * In any method invocation in which you're passing in 'exactly one argument'
 * you can opt to use curly braces instead of parentheses to surround the argument
 */

// using 'currying', you can redefine 'withPrintWriter' signature like this:
def withPrintWriter(file: File)(op: PrintWriter => Unit)

// which now enables you to call the function with a more pleasing syntax:
val file = new File("date.txt")
withPrintWriter(file) { // this curly brace is the second parameter
    writer => writer.println(new java.util.Date)
}
```

* 218 - **By-name parameters**

> - typically, parameters to functions are *by-value* parameters, meaning, the value of the parameter is determined before it is passed to the function
> - to write a function that accepts an expression that is not evaluated until it's called within a function, you use *call-by-name* mechanism, which passes a code block to the callee and each time the callee accesses the parameter, the code block is executed and the value is calculated:

```scala
var assertionsEnabled = true
def myAssert(predicate: () => Boolean) =  // without by-name parameter
  if (assertionsEnabled && !predicate())  // call it like this: myAssert(() => 5 > 3)
    throw new AssertionError

// to make a by-name parameter, you give the parameter a type
// starting with '=>' instead of '() =>'
def myAssert(predicate: => Boolean) =     // with by-name parameter
  if (assertionsEnabled && !predicate())  // call it like this: myAssert(5 > 3)
    throw new AssertionError              // which looks exactly like built-in structure

// we could've used a plain-old Boolean, but then the passed expression
// would get executed before the call to 'boolAssert'
```

### Composition and Inheritance
* 222 - **Composition** means one class holds a reference to another
* 224 - A method is **abstract** if it does not have an implementation (i.e., no equals sign or body)

> - unlike Java, no abstract modifier is allowed on method declarations
> - methods that do have an implementation are called **concrete**

* 224 - Class is said to **declare an abstract method** and that it **defines a concrete method** (i.e. *declaration* is *abstract*, *definition* is *concrete*)
* 225 - Methods with empty parentheses are called **empty-paren methods**

> - this convention (see _bullet 127_ on top) supports the __uniform access principle__, which says that the client code should not be affected by a decision to implement an attribute as a field or as a method
> - from the client's code perspective, it should be irrelevant whether `val` or `def` is accessed
> - the only difference is speed, since fields are precomputed when the class is initialized
> - but, on the other hand, fields are carried around with the parent object

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

### Scala's Hierarchy
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
* 256 - `Null` is a subclass of every reference class (i.e. class that inherits from `AnyRef`). It's not compatible with *value types* (`val i: Int = Null // type mismatch`)
* `Nothing` is a subtype of every other type (of *Null* also). There are no values of this type, it's used primarily to signal abnormal termination:

```scala
def error(message: String): Nothing =
  throw new RuntimeException(message)

// because of its position in type hierarchy, you can use it like this:
def divide(x: Int, y: Int): Int =     // must return 'Int'
  if(y != 0) x / y                    // Returns 'Int'
  else error("can't divide by zero")  // 'Nothing' is a subtype of 'Int'
```

### Traits
* 258 - **Trait** encapsulates method and field definitions, which can then be reused by mixing them into classes

> - *trait* can be mixed in using keywords `extends` or `with`. The difference is that, by using `extends`, you implicitly inherit the trait's superclass (`AnyRef` if a trait has no explicit superclass)
> - *trait* also defines a type which can be used as a regular class
> - if you want to mix a trait into a class that explicitly extends a superclass, use `extends` to indicate the superclass and `with` to mix in the trait:
> - to mix in multiple traits using `with`:
> - a class can override trait's members (polymorphism works the same way as with regular classes):

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
val queue = new BasicIntQueue with Doubling with Filtering  // filtering is applied first
// queue: BasicIntQueue with Doubling with Filtering = $anon$1@5fa12d

queue.put(10)  // passes the Filtering and then gets doubled with Doubling trait
queue.put(-1)  // not placed in the queue (negative number filter trait applied)
queue.get()  // Int = 20
```

![Scala Linearization](https://github.com/mbonaci/scala/blob/master/resources/Scala-linearization-example.png?raw=true)
![Linearization order](https://github.com/mbonaci/scala/blob/master/resources/Scala-linearization.png?raw=true)

* 275 - **When to use a _trait_ and when an _abstract class_**

> - if the behavior will not be reused make a concrete class
> - if it might be used in multiple, unrelated classes, use a trait
> - if you want to inherit from it in Java code, use an abstract class
>   - a trait with only abstract members translates to Java `interface`
> - if you plan to distribute it in compiled form and you expect others to write classes that inherit from your code, use an abstract class (when a trait gains or loses a member, any class that inherit from it must be recompiled)
> - if efficiency is very, very important, use a class (in Java, a virtual method invocation of a class member is faster than an interface method invocation)
> - if none of the above fits your case, use trait

### Packages and Imports
* 278 - **Packages** can be used like in C#: `package pkg_name { // source... }`, with more packages in a single source file. Also, they can be nested in one another

> - a package represents a scope, whose contents is accessed relative to current location
> - a top level package that's outside all packages any user can write is called `_root_`
> - all names accessible outside packages can be access from inside the package in the same way
> - if you stick with one package per file then Java package rules apply

```scala
// this
package one
package two
// is just syntactic sugar for this
package one {
  package two {

// to import the package (not a specific package member)
import one.two  // and then use objects like this: `two.Two.method`

// which is a shorthand for 'Import selector' syntax:
import one.{two}

// to access all members of a package (underscore instead of Java's star)
import one.two.three._  // could also be written as `import one.two.three.{_}`

// to use import with objects and classes
def showOne(one: One) {  // imports all members of its parameter `one`, of class `One`
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

### Assertions and Unit Testing
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

### Case Classes and Pattern Matching
* 310 - **Case classes**

> - for classes with `case` modifier, Scala compiler adds some syntactic sugar:
>   - a factory method with the same name as the class, which allows you to create new object without keyword `new` (`val m = MyCls("x")`)
>   - all class parameters implicitly get a `val` prefix, so they are made into fields
>   - compiler adds "natural" implementations of methods `toString`, `hashCode` and `equals`, which will print, hash and compare a whole tree of the class and its arguments
>   - `copy` method is added to the class (used to create modified copies). To use it, you specify the changes by using *named parameters* and for any param you don't specify, the original value is used:

```scala
scala> abstract class Expr
scala> case class Var(name: String) extends Expr
scala> case class Number(num: Double) extends Expr
scala> case class UnOp(operator: String, arg: Expr) extends Expr
scala> case class BinOp(operator: String, left: Expr, right: Expr) extends Expr
scala> val op = BinOp("+", Number(1), Var("x"))
op: BinOp = BinOp(+,Number(1.0),Var(x))

// copy method example
scala> op.copy(operator = "-")
// BinOp = BinOp(-,Number(1.0),Var(x))
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

// the right hand side can be empty (the result is 'Unit'):
case _ =>
```

> - `match` expression is evaluated by trying each of the patterns in the order they are written. The first pattern that matches is selected and the part following the fat arrow is executed
> - `match` _is an expression_ in Scala (always results in a value)
> - there is _no *fall through*_ behavior into the next case
> - if _none of the patterns match_, an exception `MatchError` is thrown

* 315 - **Constant patterns**

> - matches only itself (comparison is done using `==`)
> - any literal, `val` or singleton object can be used as a constant

```scala
def describe(x: Any) = x match {
  case 5 => "five"
  case true => "truth"
  case "hello" => "hi"
  case Nil => "the empty list"  // built-in singleton
  case _ => "something unexpected"
}
```

* 316 - **Variable patterns**

> - matches any object, like wildcard
> - unlike the wildcard, Scala binds the variable to whatever the object is and then a variable refers to that value in the right hand side of the `case` clause

```scala
import math.{E, Pi}

val pi = math.Pi

E match {
  case 0 => "zero"
  case Pi => "strange! " + E + " cannot be " + Pi
  case `pi` => "strange? Pi = " + pi  // will be treated as constant ('val pi')
  case pi => "That could be anything: " + pi  // not constant pattern ('val pi'). Variable pattern!
  case _ => "What?"  // Compiler reports "Unreachable code" error
}
/*
 * How does Scala know whether 'Pi' is a constant from 'scala.math' and not a variable?
 * A simple lexical rule is applied:
 *   - If a name starts with a lowercase letter Scala treats it as a variable pattern.
 *   - All other references are treated as constants
 *   - With exception of fields: 'this.pi' and 'obj.pi', and lowercase names in back ticks
 */
```

* 314 - **Wildcard patterns**

> - `_` matches every value, but it doesn't result with a variable

```scala
// in this example, since we don't care about elements of a binary operation
// only whether it's a binary operation or not, we can use wildcard pattern:
expr match {
  case BinOp(_, _, _) => println(expr + "is a binary operation")
  case _ => println("It's something entirely different")
}
```

* 318 - **Constructor patterns**
 
> - Scala first checks whether the object is a member of the named *case class* and then checks that the constructor params of the object match the patterns in parentheses
> - **Deep matching** means that it looks for patterns arbitrarily deep

```scala
// first checks that the top level object is a 'BinOp', then whether the third
// constructor param is a 'Number' and finally that the value of that number is '0'
expr match {
  case BinOp("+", e, Number(0)) => println("a deep match")  // checks 3 levels deep
  case _ =>
}
```

* 318 - **Sequence patterns**
 
> - `List` and `Array` can be matched against, just like *case classes*

```scala
// checks for 3 element list that starts with zero:
expr match {
  case List(0, _, _) => println("zero starting list of three elements")
}

// to check against the sequence without specifying how long it must be:
expr match {
  case List(0, _*) => println("zero starting list")
  case List(_*) => println("any list")
}
```

* 319 - **Tuple patterns**

```scala
("a ", 3, "-tuple") match {
    case (a, b, c) => println("matched " + a + b + c)  // matched a 3-tuple
    case _ =>
  }
```

* 319 - **Typed patterns**

> - used for convenient type checks and type casts

```scala
  def generalSize(x: Any) = x match {
    case s: String => s.length  // type check + type cast - 's' can only be a 'String'
    case m: Map[_, _] => m.size
    case _ => -1
  }                                               //> generalSize: (x: Any)Int

  generalSize("aeiou")                            //> Int = 5
  generalSize(Map(1 -> 'a', 2 -> 'b'))            //> Int = 2
  generalSize(math.Pi)                            //> Int = -1

// generally, to test whether expression is an instance of a type:
expr.isInstanceOf[String]  // member of class 'Any'
// to cast
expr.asInstanceOf[String]  // member of class 'Any'

def isIntToIntMap(x: Any) = x match {
  // non-variable type Int is unchecked since it's eliminated by erasure
  case m: Map[Int, Int] => true
  case _ => false
}

isIntToIntMap(Map(1 -> 2, 2 -> 3))              //> Boolean = true
isIntToIntMap(Map("aei" -> "aei"))              //> Boolean = true !!!

// the same thing works fine with arrays since their type is preserved with their value
```

> - **Type erasure**
>   - erasure model of generics, like in Java, means that no information about type arguments is maintained at runtime. Consequently, there is no way to determine at runtime whether a given Map object has been created with two Int arguments, rather than with arguments of any other type. All the system can do is determine that a value is a Map of some arbitrary type parameters

* 323 - **Variable binding**

> - allows you to, if the pattern matches, assign a variable to a matched object
> - the syntax is `var_name @ some_pattern'

```scala
case UnOp("abs", e @ UnOp("abs", _)) => e  // if matched, 'e' will be 'UnOp("abs", _)'
```

* 324 - **Pattern guards**

> - in some circumstances, syntactic pattern matching is not precise enough
> - a pattern guard comes after a pattern and starts with an `if`
> - can be arbitrary boolean expression and typically refers to pattern variables
> - the pattern matches only if the guard evaluates to `true`

```scala
// match only positive integers
case n: Int if 0 < n => n + " is positive"
// match only strings starting with the letter ‘a’
case s: String if s(0) == 'a' => s + " starts with letter 'a'"
```

> - e.g. if you'd like to transform `x + x` to `2 * x` with patterns:

```scala
// this won't work, since a pattern variable may only appear once in a pattern:
def simplifyAdd(e: Expr) = e match {
    case BinOp("+", x, x) => BinOp("*", x, Number(2))  // x is already defined as value x
    case _ => e
}

// so instead:
  def simplifyAdd(e: Expr) = e match {
    // matches only a binary expression with two equal operands
    case BinOp("+", x, y) if x == y => BinOp("*", x, Number(2))
    case _ => e
  }                     //> simplifyAdd: (e: Expr)Expr

  val add = BinOp("+", Number(24), Number(24))
                        //> add  : BinOp = BinOp(+,Number(24.0),Number(24.0))
  val timesTwo = simplifyAdd(add)
                        //> timesTwo  : Expr = BinOp(*,Number(24.0),Number(2.0))
```

* 325 - **Pattern overlaps**

> - patterns are tried in the order in which they are written

```scala
// recursively call itself until no more simplifications are possible
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
}

implicit def intToNumber(x: Int) = new Number(x)
implicit def numberToInt(x: Number) = x.num.toInt

val allMin = UnOp("-", UnOp("-", 4))
val allAdd = BinOp("+", 244, 0 + Number(0))
val allMul = BinOp("*", 24, 1)

simplifyAll(allMin)   //> Expr = Number(4.0)
simplifyAll(allAdd)   //> Expr = Number(244.0)
simplifyAll(allMul)   //> Expr = Number(24.0)
```

* 326 - **Sealed classes**

> - how can you be sure you covered all the cases when using pattern matching, since a new `case class` may be created in any time, in another compilation unit?
> - you make the _superclass_ of your _case class_ `sealed`, which then means that a class cannot have any new subclasses added except the ones in the same file
> - this way, when using pattern matching, you only need to worry about the subclasses you know of
> - also, when you match against subclasses of a sealed class, you get the compiler support, which will flag missing combinations of patterns with a warning message:

```scala
// if you make 'Expr' class sealed
sealed abstract class Expr

// and leave out some patterns when matching
def describe(e: Expr): String = e match {
  case Number(_) => "a num"
  case Var(_) => "a var"
}

/* you'll get a compiler warning:
 * warning: match is not exhaustive
 * missing combination  UnOp
 * missing combination  BinOp
 *
 * which is telling you that you might get 'MatchError'
 * because some possible patterns are not handled
 */
// 
// 

// to get rid of the warning, in situations where you're sure that no such pattern will ever appear, throw in the last catch-all case:
case _ => throw new RuntimeException  // should never happen

// the same problem can be solved with more elegant solution, without any dead code:
def describe(e: Expr): String = (e: @unchecked) match {
  case Number(_) ...
}
```

* 328 - **The Option type**

> - `Option` is type for optional values, which can be of two forms:
>   - `Some(x)`, where `x` is the actual value
>   - `None` object, which represents non-existent value
> - optional values are produced by some of the standard operations on collections, e.g. the `Map`'s `get` method produces `Some(value)` or `None` if there was no given key
> - the common way to distinguish between optional objects is through pattern matching:

```scala
def show(x: Option[String]) = x match {
  case Some(s) => s
  case None => "?"
}
```

* 330 - **Patterns in variable definitions**

> - patterns could be used for `Tuple` destructuring:

```scala
val myTuple = (123, "abc")
val (number, string) = myTuple  // multiple variables in one assignment
```

> - you can deconstruct a _case class_ with a pattern:

```scala
val exp = new BinOp("*", Number(5), Number(10))
val BinOp(op, left, right) = exp
/*
 * op: String = *
 * left: Expr = Number(5.0)
 * right: Expr = Number(10.0)
*/
```

* 331 - **Case sequences as partial functions**

> - a sequence of cases can be used anywhere a function literal can
> - essentially, a case sequence is a function literal, only more general
> - instead of having a single entry point and list of params, a case sequence has multiple entry points, each with their own list of params

```scala
val withDefault: Option[Int] => Int = {
  case Some(x) => x
  case None => 0
}
```

> - a sequence of cases gives you a *partial function*

```scala
// this will work for list of 3 elements, but not for empty list
val second: List[Int] => Int = {
  case x :: y :: _ => y
}  // warning: match is not exhaustive! missing combination     Nil

second(List(1, 2, 3))  // returns 2
second(List())         // throws MatchError

/*
 * type 'List[Int] => Int' includes all functions from list of integers to integers
 * type 'PartialFunction[List[Int], Int]' includes only partial functions
 */

// to tell the compiler that you know you're working with partial functions:
val second: PartialFunction[List[Int], Int] = {
  case x :: y :: _ => y
}

// partial functions have a method 'isDefinedAt':
second.isDefinedAt(List(5, 6, 7))  // true
second.isDefinedAt(List())  // false

/* 
 * these expressions above get translated by the compiler to a partial function
 * by translating the patterns twice, once for the implementation of the real function
 * and once to test whether the function is defined or not
 */

// e.g. the function literal
{ case x :: y :: _ => y }

// gets translated to the following partial function value:
new PartialFunction[List[Int], Int] {
  def apply(xs: List[Int]) = xs match {
    case x :: y :: _ => y
  }
  def isDefinedAt(xs: List[Int]) = xs match {
    case x :: y :: _ => true
    case _ => false
  }
}

// the translation takes place whenever the declared type of a function literal is 'PartialFunction'
// if the declared type is just 'Function1', or is missing, the function literal gets 
// translated to a complete function

// if you can, use a complete function, because partial functions allow for runtime errors
// that the compiler cannot spot

// if you happen to e.g. use a framework that expects partial function, you should
// always check 'isDefinedAt' before calling the function
```

* 334 - **Patterns in `for` expressions**

```scala
for((country, city) <- capitals)
  println("The capital of " + country + " is " + city)

// in the above example, 'for' retrieves all key/value pairs from the map
// each pair is then matched against the '(country, city)' pattern

// to pick elements from a list that match a pattern:
val results = List(Some("apple"), None, Some("orange"))
for(Some(fruit) <- results) println(fruit)
// apple
// orange

// 'None' does not match pattern 'Some(fruit)'
```

### Working with Lists
* 344 - **List literals**

> - lists are _immutable_ (list elements cannot be changed by assignment)
> - lists are _homogeneous_ (all list elements have the same type)
> - list type is _covariant_ (if `S` is subtype of `T`, then `List[S]` is a subtype of `List[T]`)
>   - `List[Nothing]` is a subtype of any other `List[T]`
>   - that is why it's possible to write `val xs: List[String] = List()`
> - they have two fundamental building blocks, `Nil` and `::` (cons), where `Nil` represents an empty list

```val nums = 1 :: 2 :: 3 :: 4 :: Nil```

* 346 - **Basic operations on lists**

> - all operations on lists can be expressed in terms of the following three methods:
>   - `head`    - returns the first list element (defined for non-empty lists)
>   - `tail`    - returns all elements except the first one (defined for non-empty lists)
>   - `isEmpty` - returns `true` if the list is empty
> - these operations take constant time, `O(1)`

```scala
// insertion sort implementation:
def isort(xs: List[Int]): List[Int] =
  if (xs.isEmpty) Nil
  else insert(xs.head, isort(xs.tail))

def insert(x: Int, xs: List[Int]): List[Int] =
  if (xs.isEmpty || x <= xs.head) x :: xs
  else xs.head :: insert(x, xs.tail)
```

* 347 - **List patterns**

> - lists can be deconstructed with pattern matching, instead of with `head`, `tail` and `isEmpty`

```scala
val fruit = "apples" :: "oranges" :: "pears"
val List(a, b, c) = fruit  // matches any list of 3, and binds them to pattern elements
// a: String = apples
// b: String = oranges
// c: String = pears

// if you don't know the number of list elements:
val a :: b :: rest = fruit  // matches list with 2 or more elements
// a: String = apples
// b: String = oranges
// rest: List[String] = List(pears)

// pattern that matches any list:
List(...)  // instance of library-defined 'extractor' pattern
```

> - normally, infix notation (e.g. `x :: y`) is equivalent to a method call, but with patterns, rules are different. When seen as a pattern, an infix operator is treated as a constructor:
>   - `x :: y` is equivalent to `::(x, y)` (not `x.::(y)`)
>   - there is a class named `::`, `scala.::` (builds non-empty lists)
>   - there is also a method `::` in class `List` (instantiates class `scala.::`)

```scala
// insertion sort implementation, written using pattern matching:
def isort(xs: List[Int]): List[Int] = xs match {
  case List() => List()
  case x :: xs1 => insert(x, isort(xs1))
}

def insert(x: Int, xs: List[Int]): List[Int] = xs match {
  case List() => List(x)
  case y :: ys =>
    if(x <= y) x :: xs
    else y :: insert(x, ys)
}
```

* 349 - **First-order methods on class List**

> - a method is _first order_ if it doesn't take any functions as arguments
> - **Concatenating two lists**
>   - `xs ::: ys` returns a new list that contains all the elements of `xs`, followed by all the elements of `ys`
>   - `:::` is implemented as a method in class `List`
>   - like `cons`, list concatenation associates to the right:

```scala
// expression like this:
xs ::: ys ::: zs
// is interpreted as:
xs ::: (ys ::: zs)
```

> - **Divide and Conquer principle**
>   - design recursive list manipulation algorithms by pattern matching and deconstruction:

```scala
// my implementation of list concatenation
def append[T](xs: List[T], ys: List[T]): List[T] = xs match {
  case List() => ys
  case x :: rest => x :: append(rest, ys)
}
```

> - **List length**
>   - `length` on lists is a relatively expensive operation, when compared to arrays (`O(n)`)

> - **Accessing the end: `init` and `last`**
>   - `last` returns the last element of a list
>   - `init` returns a list consisting of all elements but the last one
>   - same as `head` and `tail`, they throw an exception when invoked on an empty list
>   - slow when compared to `head` and `tail` since they take linear time, `O(n)`

> - **Reversing lists: `reverse`**
>   - it's better to organize your data structure so that most accesses are at the head of a list
>   - if an algorithm demands frequent access to the end of a list, it's better to reverse the list first

```scala
// 'reverse' implemented using concatenation
def rev[T](xs: List[T]): List[T] = xs match {
  case List() => xs
  case x :: rest => rev(rest) ::: List(x)  // slow: n-1 recursive calls to concatenation 
  // alt. with my concatenation
  // case x :: rest => append(rev(rest), List(x))
}
```

> - **Prefixes and suffixes: `drop`, `take` and `splitAt`**
>   - `xs take n` returns the first `n` elements of the list `xs` (if `n > xs.length` the whole `xs` is returned)
>   - `xs drop n` returns all elements of the list `xs` except the first `n` ones (if `n > xs.length` the empty list is returned)
>   - `xs splitAt n` will return two lists, the same as `(xs take n, xs drop n)`, only it traverses the list just once

> - **Element selection: `apply` and `indices`**
>   - `xs apply n` returns _n-th_ element
>     - as for all other types, `apply` is implicitly inserted when an object appears in the function position in a method call, so the example from the line above can be written as `xs(n)`
>     - `apply` is implemented as `(xs drop n).head`, thus it's slow (`O(n)`) and rarely used on lists, unlike with arrays
>   - `List(1, 2, 3).indices` returns `scala.collection.immutable.Range(0, 1, 2)`

> - **Flattening a list of lists: `flatten`**
>   - takes a list of lists and flattens it out to a single list
>   - it can only be used on lists whose elements are all lists (compilation error otherwise)

```scala
fruit.map(_.toCharArray).flatten
// List(a, p, p, l, e, s, o, r, a, n, g, e, s, p, e, a, r, s)
```

> - **Zipping lists: `zip` and `unzip`**
>   - `zip` takes two lists and pairs the elements together, dropping any unmatched elements
>   - a useful method is also `zipWithIndex`, which pairs every element with its index
>   - `unzip` converts list of tuples to tuple of lists

```scala
List('a', 'b') zip List(1, 2, 3)
// List[(Char, Int)] = List((a,1), (b,2))

val zipped = List('a', 'b').zipWithIndex
// zipped: List[(Char, Int)] = List((a,0), (b,1))

zipped.unzip
// (List[Char], List[Int]) = (List(a, b), List(1, 2))
```

> - **Displaying lists: `toString` and `mkString`**
>   - members of the `Traversable` trait, which makes them applicable to all other collections

```scala
// 'toString' returns canonical representation of a list:
val abc = List("a", "b", "c")
abc.toString  // List(a, b, c)

// 'mkString' is more suitable for human consumption:
mkString(pre, sep, post)  // returns:
pre + xs(0) + sep + xs(1) + sep + ... + sep + xs(xs.length - 1) + post

// also:
xs mkString sep  // equals
xs mkString("", sep, "")  // also, you can omit all arguments (default to empty string)

// a variant of 'mkString' which appends string to a 'scala.StringBuilder' object:
val buf = new StringBuilder
abc addString(buf, "(", "; ", ")")  // StringBuilder = (a; b; c)
```

> - **Converting lists: `iterator`, `toArray` and `copyToArray`**
>   - `toArray` converts a list to an array and `toList` does the opposite

```scala
val arr = abc.toArray  // Array(a, b, c)
val xs = arr.toList  // List(a, b, c)

// to copy all elements of the list to an array, beginning with position 'start':
xs copyToArray (arr, start)

// before copying, you must ensure that the array is large enough:
val arr2 = new Array[String](7)
xs copyToArray (arr2, 3)  // produces 'Array(null, null, null, a, b, c, null)'

// to use an iterator to access list elements:
val it = abc.iterator  // it: Iterator[String] = non-empty iterator
it.next  // String = "a"
it.next  // String = "b"
```

> - **Merge sort example**
>   - faster than _insertion sort_ for lists - `O(n log(n))`

```scala
  def msort[T](less: (T, T) => Boolean)(xs: List[T]): List[T] = {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xs1, y :: ys1) =>
        if(less(x, y)) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
    }
    
    val n = xs.length / 2
    if(n == 0) xs
    else {
      val (ys, zs) = xs splitAt n
      merge(msort(less)(ys), msort(less)(zs))
    }
  }

// call it like this:
val res = msort((x: Int, y: Int) => x < y)(9 :: 1 :: 8 :: 3 :: 2 :: Nil)
```

>   - _currying_ helps us to create specialized functions, predetermined for a particular comparison operation:

```scala
// reverse sort (underscore stands for missing arguments list, in this case, a list that should be sorted)
val reverseIntSort = msort((x: Int, y: Int) => x > y) _
// reverseIntSort: (List[Int]) => List[Int] = <function>

reverseIntSort(9 :: 1 :: 8 :: 3 :: 2 :: Nil)
```

* 361 - **Higher-order methods on class List**

> - allow you to express useful list operation patterns in a more concise way

> - **Mapping over lists: `map`, `flatMap` and `foreach`**
>   - `xs map f`, where `xs` is some `List[T]` and `f` is a function of type `T => U`, applies the function `f` to each list element and returns the resulting list

```scala
List(1, 2, 3) map (_ + 1)  // returns 'List(2, 3, 4)'

val words = List("the", "quick", "brown", "fox")
words map (_.length)  // 'List(3, 5, 5, 3)'
words map (_.toList.reverse.mkString) // 'List(eht, kciuq, nworb, xof)'

// 'flatMap' takes a function returning a list of elements as its right operand,
// which it then applies to each list element and flattens the function results
words flatMap (_.toList)  // 'List(t, h, e, q, u, i, c, k, b, r, o, w, n, f, o, x)'

// 'map' and 'flatMap' together:
List.range(1, 5) flatMap (i => List.range(1, i) map (j => (i, j)))
// List[(Int, Int)] = List((2,1), (3,1), (3,2), (4,1), (4,2), (4,3))
// 'range' creates a list of all integers in some range, excluding second operand

// equivalent to:
for(i <- List.range(1, 5);
    j <- List.range(1, i)) yield (i, j)

// 'foreach' takes a procedure (a function resulting with Unit) as its right operand,
// and applies the procedure to each list element. The result is Unit, not a new list
var sum = 0
List(1, 2, 3, 4, 5) foreach (sum += _) // sum: Int = 15
```

> - **Filtering lists: `filter`, `partition`, `find`, `takeWhile`, `dropWhile` and `span`**

```scala
// 'filter' takes a list and a predicate function and returns the new list containing 
// the elements that satisfy the predicate
val xs = List(1, 2, 3, 4, 5)
xs filter (_ % 2 == 0)  // List(2, 4)

// 'partition' returns a pair of lists, one with elements that satisfy the predicate
// and the other with ones that don't: '(xs filter p, xs filter (!p(_)))'
xs partition (_ % 2 == 0)  // (List(2, 4), List(1, 3, 5))

// 'find' is similar to 'filter', but returns only the first element, or 'None'
xs find (_ % 2 == 0)  // Some(2)
xs find (_ <= 0)  // None

// 'takeWhile' returns the longest prefix that satisfy the predicate
val ys = List(1, 2, 3, 4, 3, 2)
ys takeWhile (_ <= 3)  // List(1, 2, 3)

// 'dropWhile' is similar to 'takeWhile', but it drops the elements and returns the rest
ys dropWhile (_ <= 3)  // List(4, 3, 2)

// 'span' returns a pair of lists, the first 'takeWhile' and the second 'dropWhile'
// like 'splitAt', 'span' traverses the list only once
ys span (_ <= 3)  // (List(1, 2, 3), List(4, 3, 2))
```

> - **Predicates over lists: `forall` and `exists`**

```scala
// 'forall' takes a list and a predicate and returns 'true' if all elements satisfy the predicate
// 'exists' is similar to 'forall', but it returns 'true' if there's at least one element
// that satisfies the predicate
def hasZeroRow(m: List[List[Int]]) =
  m exists (row => row forall (_ == 0))

val y = List(0, -1)
val z = List(0, 0, 0)
val zz = List(y, y, z)  //> List(List(0, -1), List(0, -1), List(0, 0, 0))
  
hasZeroRow(zz)          //> Boolean = true
```

> - **Folding lists: `/:` and `:\`**
>   - _folding_ combines the elements of a list with some operator
>   - there are equivalent methods named `foldLeft` and `foldRight` defined in class `List`

```scala
sum(List(1, 2, 3))  // equals 0 + 1 + 2 + 3
// which is a special instance of a fold operation:
def sum(xs: List[Int]): Int = (0 /: xs) (_ + _)  // equals 0 + el1 + el2 + ...

def product(xs: List[Int]): Int = (1 /: xs) (_ * _)  // equals 1 * el1 * el2 * ...
```

>   - **fold left** operation `(z /: xs)(op)` involves 3 objects:
>     - start value `z`
>     - list `xs`
>     - binary operation `op`
>   - `(z /: List(a, b, c))(op)` equals `op(op(op(z, a), b), c)`

![List fold left image](https://github.com/mbonaci/scala/blob/master/resources/Scala-fold-left.png?raw=true)

>   - **fold right** operation `(z \: xs)(op)` is the reflection of _fold left_
>   - consists of the same 3 operands, but the first two are reversed, list comes first
>   - `(List(a, b, c) :\ z)(op)` equals `op(a, op(b, op(c, z)))`

![List fold right image](https://github.com/mbonaci/scala/blob/master/resources/Scala-fold-right.png?raw=true)

```scala
// implementation of the 'flatten' methods with folding:
def flattenLeft[T](xss: List[List[T]]) =
  (List[T]() /: xss) (_ ::: _)  // less efficient, since it copies 'xss' 'n - 1' times

def flattenRight[T](xss: List[List[T]]) =
  (xss :\ List[T]()) (_ ::: _)

// '(xs ::: ys)' takes linear time 'xs.length'
// '[T]' is required due to a limitation in type inferencer

// list reversal implemented using fold left (takes linear time):
def reverseLeft[T](xs: List[T]) =
  (List[T]() /: xs) {(ys, y) => y :: ys}  // "snoc" ("cons" reversed)

// how we implemented the function:

/* First we took smallest possible list, 'List()':
  equals (by the properties of reverseLeft)
    reverseLeft(List())
  equals (by the template for reverseLeft)
    (startvalue /: List())(operation)
  equals (by the definition of /:)
    startvalue
*/

/* Then we took the next smallest list, 'List(x)':
  equals (by the properties of reverseLeft)
    reverseLeft(List(x))
  equals (by the template for reverseLeft)
    (List() /: List(x)) (operation)
  equals (by the definition of /:)
    operation(List(), x)
*/
```

> - **Sorting lists: `sortWith`**
>   - `xs sortWith before`, where `before` is a function that compares two elements
>   - `x before y` should return `true` if `x` should come before `y` in a sort order
>   - uses _merge sort_ algorithm

```scala
list(1, -2, 8, 3, 6) sortWith (_ < _)
// List(-2, 1, 3, 6, 8)

words sortWith (_.length > _.length)
// List(quick, brown, the, fox)
```

* 369 - **Methods of the List object**

> - all the methods above are implemented in class `List`, whereas the following ones are defined in globally accessible, `List` class's companion object `scala.List`

> - **Creating lists from their elements: `List.apply`**
>   - `List(1, 2)` is in fact `List.apply(1, 2)`

> - **Creating a range of numbers: `List.range`**

```scala
// simplest form
List.range(1, 4)      // List(1, 2, 3)
List.range(1, 7, 2)   // List(1, 3, 5)
List.range(9, 1, -3)  // List(9, 6, 3)
```

> - **Creating uniform lists: `List.fill`**
>   - creates a list consisting of zero or more copies of the same element

```scala
// use currying when invoking it:
List.fill(3)('a')  // List(a, a, a)
List.fill(2)("oy")  // List(oy, oy)

// with more than one argument in the first arg list, it'll make multi-dimensional list
List.fill(2, 3)('b')  // List(List(b, b, b), List(b, b, b))
```

> - **Tabulating a function: `List.tabulate`**
>   - similar to `fill`, only element isn't fixed, but computed using supplied function

```scala
val squares = List.tabulate(5)(n => n * n)  // one list with 5 elements
// List(0, 1, 4, 9, 16)

val multiplication = List.tabulate(3, 4)(_ * _)  // 3 lists with 4 elements
// List[List[Int]] = List(List(0, 0, 0, 0), List(0, 1, 2, 3), List(0, 2, 4, 6))
/*
    0  1  2  3

0   0  0  0  0
1   0  1  2  3
2   0  2  4  6
*/
```

> - **Concatenating multiple lists: `List.concat`**

```scala
List.concat(List(), List('b'), List('c'))  // List(b, c)
List.concat()  // List()
```

* 371 - **Processing multiple lists together**

> - `zipped`, `map`, `forall`, `exists`
> - `zipped` method (defined on tuples) combines the tuple elements sequentially, same as `zip`, first with first, second with second, ...
> - it is used with other (multiple list) methods to apply an operation to combined elements

```scala
(List(4, 6, 1), List(5, 8)).zipped.map(_ * _)   // List(20, 48)

(List("on", "mali", "debeli"), List(2, 4, 5)).zipped
    .forall(_.length == _)                      //> false (two matches)
(List("on", "mali", "debeli"), List(1, 4, 9)).zipped
    .exists(_.length == _)                      // true (one matches)
(List("on", "mali", "debeli"), List(2, 4, 6)).zipped
    .exists(_.length != _)                      // false (all matches)
```

* 372 - **Understanding Scala's type inference algorithm**

> - the goal of type inference is to enable users of your method to give as less type information possible, so that function literals are written in more concise way
> - type inference is flow based
> - in a method application `m(args)`, the inferencer:
> 
>   - first checks whether the method `m` has a known type
> 
>   - if it has, that type is used to infer the expected type of arguments
>     - e.g. in `abcde.sortWith(_ > _)` the type of `abcde` is `List[Char]`
>     - so it knows `sortWith` takes `(Char, Char) => Boolean` and produces `List[Char]`
>     - thus, it expands `(_ > _)` to `((x: Char, y: Char) => x > y)`
> 
>   - if the type is not know
>     - e.g. in `msort(_ > _)(abcde)`, `msort` is curried, polymorphic method that takes an argument of type `(T, T) => Boolean` to a function from `List[T]` to `List[T]` where `T` is some as-yet unknown type
>     - the `msort` needs to be instantiated with a specific type parameter before it can be applied to its arguments
>     - inferencer changes its strategy and type-checks method arguments to determine the proper instance type of the method, but it fails, since all it has is `(_ > _)`
>     - one way to solve the problem is to supply `msort` with explicit type parameter
>       - `msort[Char](_ > _)(abcde)  // List(e, d, c, b, a)`
>     - another solution is to rewrite `msort` so that its parameters are swapped:

```scala
def msortSwapped[T](xs: List[T])(less: 
  (T, T) => Boolean): List[T] = {
  // impl
}

msortSwapped(abcde)(_ > _)  // succeeds to compile
// List(e, d, c, b, a)
```

>   - generally, when tasked to infer type parameters of a polymorphic method, the inferencer consults the types of all value arguments in the first parameter list, but it doesn't go beyond that
>   - so, when we swapped the arguments, it used the known type of the first parameter `abcde` to deduce the type parameter of `msortSwapped`, so it did not need to consult the second argument list in order to determine the type parameter of the method
>   - **suggested library design principle:**
>     - when designing a polymorphic method that takes a non-function and function arguments, place the function argument last in a curried parameter list by its own
>     - that way, the method's correct instance type can be inferred from the non-function arguments, and then that type can be used to type-check the function argument

### Collections
* 377 - **Sequences**

> - groups of data lined up in order, which allows you to get the 'n-th' element
> - **Lists** (immutable linked list)
>   - support fast addition and removal of items to the beginning of the list
>   - slow in manipulating the end of a sequence (add to front and reverse in the end)
>   - do not provide fast access to arbitrary indexes (must iterate through the list)
> - **Arrays**
>   - fast access of an item in an arbitrary position (both, get and update)
>   - represented in the same way as Java arrays (use Java methods that return arrays)

```scala
// to create an array whose size you know, but you don't know element values
val fiveInts = new Array[Int](5)  // Array(0, 0, 0, 0, 0)

// to initialize an array when you know the element values:
val fiveToOne = Array(5, 4, 3, 2 , 1)

// read and update:
fiveInts(0) = fiveToOne(4)
fiveInts  // Array(1, 0, 0, 0, 0)
```

> - **List buffers** (mutable)
>   - used when you need to build a list by appending to the end
>   - constant time append and prepend operations
>   - `+=` to append, and `+=:` to prepend
>   - when you're done, you can obtain a list with the `toList` method of `ListBuffer`
>   - if your `List` algorithm is not tail recursive, you can use `ListBuffer` with `for` or `while` to avoid the potential stack overflow

```scala
import scala.collection.mutable.ListBuffer
val buf = new ListBuffer[Int]

buf += 22   // ListBuffer(22)
11 +=: buf  // ListBuffer(11, 22)
buf.toList  // List(11, 22)
```

> - **Array buffers** (mutable)
>   - like an array, but you can add and remove elements from the beginning and the end
>   - all `Array` operations are available, though little slower, due to wrapping layer in the implementation
>   - addition and removal take constant time on average, but require linear time if the array needs to be expanded

```scala
import scala.collection.mutable.ArrayBuffer
val abuf = new ArrayBuffer[Int]()

// append using '+='
abuf += 8                   // ArrayBuffer(8)
abuf += 4                   // ArrayBuffer(8, 4)

abuf.length                 // Int = 2
abuf(1)                     // Int = 4
```

> - **Strings** (via **StringOps**)
>   - since `Predef` has an implicit conversion from `String` to `StringOps`, you can use any string like a sequence

```scala
def hasUpperCaseLetter(s: String) = s.exists(_.isUpper)  // String doesn't have 'exists'

hasUpperCaseLetter("glupson 1")  // false
hasUpperCaseLetter("glupsoN 1")  // true
```

* 381 - **Sets and maps**

> - by default, when you write `Set` or `Map`, you get an immutable object
> - for mutable objects, you need explicit import

```scala
object Predef {
  type Map[A, +B] = collection.immutable.Map[A, B]
  type Set[A] = collection.immutable.Set[A]
  val Map = collection.immutable.Map
  val Set = collection.immutable.Set
  // ...
}
// the 'type' keyword is used in 'Predef' to define aliases for fully qualified names
// the 'vals' are initialized to refer to the singleton objects
// so 'Map' == 'Predef.Map' == 'scala.collection.immutable.Map'

// to use the immutable and mutable in the same source file:
import scala.collection.mutable
val mutaSet = mutable.Set(1, 2)  // scala.collection.mutable.Set[Int]
val set = Set(1, 2)              // scala.collection.immutable.Set[Int]
```

> - **Using sets**
>   - the key characteristic is that they maintain uniqueness of their elements, as defined by `==`

```scala
val text = "run Forest, run. That's it Forest! Run!"
val wordsArray = text.split("[ !,.]+")  // Array(run, Forest, Run, That's, it, Forest, Run)
import scala.collection.mutable
val set = mutable.Set.empty[String]  // Set()

for(word <- wordsArray)
  set += word.toLowerCase
  
set  // Set(it, run, that's, forest)
```

![Set hierarchy](https://github.com/mbonaci/scala/blob/master/resources/Scala-sets-hierarchy.png?raw=true)

```scala
/********************************************************************************/
/*********************      Common operations for sets      *********************/
/********************************************************************************/

val nums = Set(1, 2, 3)          // crates an immutable set

nums.toString                    // returns Set(1, 2, 3)

nums + 5                         // adds an element (returns Set(1, 2, 3, 5))

nums - 3                         // removes the element (returns Set(1, 2))

nums ++ List(5, 6)               // adds multiple elements (returns Set(1, 2, 3, 5, 6))

nums -- List(1, 2)               // removes multiple elements

nums & Set(1, 3, 5, 7)           // returns the intersection of two sets (Set(1, 3))

nums.size                        // returns the size of the set

nums.contains(3)                 // checks for inclusion

import scala.collection.mutable  // makes the mutable collections easy to access

val words = mutable.Set.empty[String]  // creates an empty, mutable set (HashSet)

words.toString                   // returns Set()

words += "the"                   // adds an element (Set(the))

words -= "the"                   // removes an element (Set())

words ++= List("do", "re", "mi") // adds multiple elements

words --= List("do", "re")       // removes multiple elements

words.clear                      // removes all elements
```

> - **Using maps**
>   - when creating a map, you must specify two types (key, value)

```scala
// word count using a map
def main(args: Array[String]): Unit = {
  val count = countWords("run forest, run fast! run forest!")
  println(count.toString)  // Map(fast -> 1, run -> 3, forest -> 2)
}

import scala.collection.mutable
def countWords(text: String): mutable.Map[String, Int] = {
  val wordsArray = text.split("[ !,.]+")
  val map = mutable.Map.empty[String, Int]
  
  for(w <- wordsArray)
    if(map.contains(w))
      map(w) = map(w) + 1
    else
      map += (w -> 1)
      
  map
}
```

![Map hierarchy](https://github.com/mbonaci/scala/blob/master/resources/Scala-maps-hierarchy.png?raw=true)

```scala
/********************************************************************************/
/*********************      Common operations for maps      *********************/
/********************************************************************************/

val m = Map("i" -> 1, "ii" -> 2)  // crates an immutable map

m.toString                        // returns Map(i->1, ii->2)

m + ("vi" -> 6)                   // adds an entry (returns Map(i->1, ii->2, vi->6)

m - "ii"                          // removes the entry (returns Map(i->1, vi->6))

m ++ List("iii" -> 5, "v" -> 5)   // adds multiple entries

m -- List("i", "ii")              // removes multiple entries

m.size                            // returns the size of the map

m.contains("ii")                  // checks for inclusion

m("ii")                           // returns 2

m.keys                            // returns Iterable over keys ("i" and "ii")

m.keySet                          // returns keys as a set (Set(i, ii))

m.values                          // returns Iterable over values (1, 2)

m.isEmpty                         // indicates whether the map is empty

import scala.collection.mutable   // makes the mutable collections easy to access

val w = mutable.Map.empty[String, Int]  // creates an empty, mutable map (HashMap)

w.toString                        // returns Map()

w += ("one" -> 1)                 // adds an entry (Map(one->1))

w -= "one"                        // removes an entry (Map())

w ++= List("st" -> 1, "nd" -> 2, "rd" - 3)  // adds multiple entries

w --= List("st", "nd")            // removes multiple entries

w.clear                           // removes all entries
```

> - **Default sets and maps**
>   - `mutable.Map()` factory method returns `mutable.HashMap` (analogous for mutable set)
>   - for immutable sets and maps, it depends on how many elements you pass to it:

```scala
// rules for sets (the same applies for maps)
Number of elements  Implementation (used to maximize performance)
0                   scala.collection.immutable.EmptySet
1                   scala.collection.immutable.Set1
2                   scala.collection.immutable.Set2
3                   scala.collection.immutable.Set3
4                   scala.collection.immutable.Set4
5 or more           scala.collection.immutable.HashSet (implemented as trie)
```

> - **Sorted sets and maps**
>   - a set or map whose iterator returns elements in a particular order
>   - for this purpose, `collections` library provides traits `SortedSet` and `SortedMap`, which are implemented using `TreeSet` and `TreeMap`, which use a **red-black** tree to keep `TreeSet` elements and `TreeMap` keys in order
>   - the order is determined by the `Ordered` trait, which the element type of set, or key type of map must either mix in or be implicitly convertible to

```scala
import scala.collection.immutable.TreeSet
val ts = TreeSet(9, 2, 5, 1, 8, 6, 4, 3)  // TreeSet(1, 2, 3, 4, 5, 6, 8, 9)

import scala.collection.immutable.TreeMap
val tm = TreeMap(8 -> 'e', 7 -> 'a', 1 -> 'w')  // Map(1 -> w, 7 -> a, 8 -> e)
val otm = tm + (2 -> 'u')  // Map(1 -> w, 2 -> u, 7 -> a, 8 -> e)
```

* 390 - **Selecting mutable versus immutable collections**

> - immutable collections can usually be stored more compactly, especially small maps and sets, e.g. empty mutable map, in its default representation, HashMap, takes around 80 bytes, with 16 bytes for every new element, while immutable Map1 takes only 16 bytes, and Map4 around 40 bytes
> - immutable map is a single object that's shared between all references, so referring to it costs just a single pointer field
> - to make the switch between mutable and immutable, Scala provides some syntactic sugar:

```scala
// if you declare immutable set or map as 'var':
var toys = Set("bear", "car")  // scala.collection.immutable.Set[String] = Set(bear, car)
toys += "doll"  // a new set is created and then 'toys' is reassigned to it
toys  // scala.collection.immutable.Set[String] = Set(bear, car, doll)

toys -= "bear"  // works with any other operator method ending with '='
toys  // scala.collection.immutable.Set[String] = Set(car, doll)

// then, if you want to switch to mutable the only thing you need is:
import scala.collection.mutable.Set

// this works with any type, not just collections
```

* 392 - **Initializing collections**

> - the common way to create and initialize a collection is to pass the initial elements to a factory method on the companion object (invokes `apply`)
> - when an inferred type is not what you need, explicitly set type of your collection:

```scala
val stuff = mutable.Set[Any](42)
stuff += "green"  // scala.collection.mutable.Set[Any] = Set(42, green)
```

* 394 - **Converting to array or list**

> - to convert a collection to array, simply call `toArray`
> - to convert a collection to list, simply call `toList`
> - there is a speed penalty, since all the elements need to be copied during the conversion process, which may be problematic for large collections
> - the order of elements in the resulting list or array will be the order produced by an iterator obtained by invoking `elements` on the source collection
> - in case of sorted collections, the resulting list or array will also be sorted:

```scala
val ts = TreeSet(8, 3, 4, 1)
val a = ts.toArray  // Array(1, 3, 4, 8)
val l = ts.toList   // List(1, 3, 4, 8)
```

* 395 - **Converting between mutable and immutable sets and maps**

> - create a collection of the new type using the `empty` method and then add the new elements using method for adding multiple entries (`++` for mutable and `++=` for immutable)

```scala
// converting immutable TreeSet to a mutable set and back
val ts = TreeSet(9, 2, 5, 1, 8, 6, 4, 3)
val ms = mutable.Set.empty ++= ts  // mutable.Set[Int] = Set(9, 1, 5, 2, 6, 3, 4, 8)
val is = Set.empty ++ mts        // immutable.Set[Int] = Set(5, 1, 6, 9, 2, 3, 8, 4)
```

* 396 - **Tuples**

> - a tuple combines a fixed number of items together so they can be passed around as a whole
> - unlike arrays and lists, tuple can hold objects of different types
> - tuples save you the effort of defining simplistic, data-heavy (as opposed to logic-heavy) classes
> - since they can contain objects of different types, they don't inherit from `Traversable`
> - a common usage pattern of tuples is returning multiple values from a method
> - to access elements of a tuple you can use methods `_1`, `_2`, ...
> - you can deconstruct a tuple like this: `val (word, idx) = someTuple2` (if you leave off the parentheses, both `word` and `idx` vals are assigned with a whole tuple)

```scala
def findLongest(words: Array[String]): Tuple2[String, Int] = {
  var len = -1
  var index = -1
  
  for(word <- words) {
    if(word.length > len) {
      index = words.indexOf(word)
      len = word.length
    }
  }
  (words(index), index)

}  // findLongest: (words: Array[String])(String, Int)

var toys = Set("bear", "car", "doll", "loading truck")
val tup = findLongest(toys.toArray)  // tup: (String, Int) = (loading truck,3)
```

### Stateful Objects
* 402 - **Reassignable variables and properties**

> - every non-private `var x` member of an object implicitly defines getter and setter
> - getter is named `x` and setter is named `x_=`
> - getter and setter inherit access from their `var`

```scala
var hour = 6
// generates
private[this] var h = 6
// and getter
hour
// and setter
hour_=

// the following two class definitions are identical:
class Time {
  var hour = 6
  var minute = 30
}

class Time {
  private[this] var h = 6  // access qualifier: private up to this (invisible outside)
  private[this] var m = 30

  def hour: Int = h
  def hour_=(x: Int) { h = x }

  def minute: Int = m
  def minute_=(x: Int) { m = x }
}

// you can define getters and setters explicitly
class Time {
  private[this] var h = 6
  private[this] var m = 30

  def hour: Int = h
  def hour_=(x: Int) {
    require(0 <= x && x < 24)
    h = x
  }
}

// getters and setters can be defined without the accompanying field:
class Thermometer {
  var celsius: Float = _  // initialized to default value (0)

  def fahrenheit = celsius * 9 / 5 + 32

  def fahrenheit_= (f: Float) {
    celsius = (f - 32) * 5 / 9
  }

  override def toString = fahrenheit + "F/" + celsius + "C"
}
```

> - initializer sets a variable to default value of that type:
>   - `0`       - for numeric types
>   - `false`   - for booleans
>   - `null`    - for reference types
> - works the same way as uninitialized variables in Java

* 405 - **Case study: Discrete event simulation**

> - internal DSL is a DSL implemented as a library inside another language, rather than being implemented on its own

### Type Parameterization
* 422 - **Information hiding**

> - to hide a primary constructor add `private` modifier in front of the class parameter list
> - private constructor can only be accessed from withing the class itself or its companion object

```scala
class ImmutableQueue[T] private (
  private val leading: List[T]
  private val trailing: List[T]
)

new Queue(List(1, 2), List(3))  // error: ImmutableQueue cannot be accessed in object $iw

// now one possibility is to add auxiliary constructor, e.g.:
def this() = this(Nil, Nil)  // takes empty lists

// auxiliary constructor that takes 'n' parameters of type 'T':
def this(elems: T*) = this(elems.toList, Nil)  // 'T*' - repeated parameters

// another possibility is to add a factory method
// convenient way of doing that is to define an ImmutableQueue object
// that contains 'apply' method
// by placing this object in the same source file with the ImmutableQueue class
// it becomes its companion object
object ImmutableQueue {
  // creates a queue with initial elements 'xs'
  def apply[T](xs: T*) = new ImmutableQueue[T](xs.toList, Nil)
}

// since a companion object contains method 'apply', clients can create queues with:
ImmutableQueue(1, 2, 3)  // expands to ImmutableQueue.apply(1, 2, 3)
```

* 428 - **Private classes**

> - more radical way of information hiding that hides a class itself
> - then, you export a trait that reveals the public interface of a class:

```scala
trait Queue[T] {
  def head: T                  // public
  def tail: Queue[T]
  def enqueue(x: T): Queue[T]
}


object Queue {
  def apply[T](xs: T*): Queue[T] =
    new QueueImpl[T](xs.toList, Nil)
    
  private class QueueImpl[T](  // private inner class
    private val leading: List[T],
    private val trailing: List[T]
  ) extends Queue[T] {         // mixes in the trait, which has access to private class
    
    def mirror =
      if (leading.isEmpty)
        new QueueImpl(trailing.reverse, Nil)
      else
        this
        
    def head: T = mirror.leading.head
    
    def tail: QueueImpl[T] = {
      val q = mirror
      new QueueImpl(q.leading.tail, q.trailing)
    }
    
    def enqueue(x: T) =
      new QueueImpl(leading, x :: trailing)
  }
  
}
```

* 429 - **Variance annotations**

> - `Queue`, as defined in previous listing is a trait, not a type, so you cannot create variables of type `Queue`
> - instead, trait `Queue` enables you to specify parameterized types, such as `Queue[String]`, `Queue[AnyRef]`
> - thus, `Queue` is a trait, and `Queue[String]` is a type
> - this kind of traits are called **type constructors** (you can construct a type by specifying a type parameter, which is analogous to plain-old constructor with specified value parameter)
> - _type constructors_ generate a family of types
> - it is also said that the `Queue` is a **generic trait**
> - in Scala, **generic types have _nonvariant_ (rigid) subtyping**
> - consequently, `Queue[String]` is not a subtype of `Queue[AnyRef]`
> - however, you can demand **covariant** (flexible) subtyping by prefixing a type parameter with `+`:  

`trait Queue[+T] { ... }`

> - besides `+` **parameter's variance annotation**, there's also a `-`, which indicates **contravariant** subtyping:  

`trait Queue[-T] { ... }`

> - then, if `T` is a subtype of `S`, this would imply that `Queue[S]` is a subtype of `Queue[T]`

* 432 - **Variance and arrays**

> - arrays in Java are treated as covariants:

```java
// Java
String[] a1 = { "abc" };
Object[] a2 = a1;
a2[0] = new Integer(8);  // ArrayStroreException (Integer placed in String[])
String s = a1[0];
```

> - because of that, arrays in Scala are nonvariant:

```scala
val a1 = Array("abc")
val a2 = Array[Any] = a1  // error: type mismatch, found Array[String], required Array[Any]
```

> - to interact with legacy methods in Java that use an `Object` array as a means to emulate generic array, Scala lets you cast an array of `T`s to an array of any supertype of T:

```scala
val a2: Array[Object] = a1.asInstanceOf[Array[Object]]
```

* 433 - **Checking variance annotations**

> - to verify the correctness of variance annotations, the compiler classifies all positions in a class or trait body as **positive**, **negative** or **neutral**
> - a _position_ is any location in the class (or trait) body where a type parameter may be used
> - e.g. every method value parameter is a position, because it has a type and therefore a type parameter could appear in that position
> type parameters annotated with `+` can only be used in _positive_ positions, `-` in negative, and a type parameter without variance annotation may be used in any position (so it's the only one that can be used in _neutral_ positions)
> - compiler classifies positions like this:
>   - the positions at the top level of the class are classified as positive
>   - positions at deeper nesting levels are classified the same as their enclosing level, but with exceptions where the classifications changes (flips):
>     - method value parameter positions are classified to the flipped classification relative to positions outside the method (when flipped, neutral stays the same, negative position becomes positive, and vice versa)
>     - classification is also flipped at the type parameters of methods
>     - it is sometimes flipped at the type argument position of a type (e.g. `Arg` in `C[Arg]`), depending on the variance of the corresponding type parameter (if C's type param is annotated with `+`, then the classification stays the same, and if it's `-`, then it flips, and if has no variance then it's changed to neutral)
> - because it's hard to keep track of variance position, it's good to know that the compiler does all the work for you

```scala
// variance checks by the compiler (postfix signs represent position classification):
abstract class Cat[-T, +U] {
  def meow[W-](volume: T-, listener: Cat[U+, T-]-): Cat[Cat[U+, T-]-, U+]+
}
// since T is always used in negative position and U in positive, the class is type correct
```

* 437 - **Lower bounds**

> - `Queue[T]` cannot be made covariant in `T` because `T` appears as a type of a parameter of the `enqueue` method, and that's a negative position
> - there's still a way to solve that problem by generalizing `enqueue` by making it polymorphic (i.e. giving the method itself a type parameter) and using a **lower bound** for its type parameter:

```scala
class Queue[+T](private val leading: List[T]), private val trailing: List[T]) {
  // defines T as the lower bound for U (U is required to be a supertype of T)
  def enqueue[U >: T](x: U) = // U is negative (flip) and T is positive (two flips)
    new Queue[U](leading, x :: trailing)
    // ...
}
// the param to 'enqueue' is now of type 'U'
// the method's return type is now 'Queue[U]', instead of 'Queue[T]'
// imagine e.g. class Fruit with two subclasses, Apple and Orange
// With the new definition of class Queue, it is possible to append an Orange to a 
// Queue[Apple] and the result will be of type Queue[Fruit]
```

* 438 - **Contravariance**

> - **Liskov Substitution Principle** says that it is safe to assume that a type `T` is a subtype of a type `U` if you can substitute a value of type `T` wherever a value of type `U` is required
> - the principle holds if `T` supports the same operations as `U` and all of `T's` operations require less and provide more than the corresponding operations in `U`

```scala
// example of Contravariance of a function parameter
class Publication(val title: String)
class Book(title: String) extends Publication(title)

object Library {
  val books: Set[Book] =
    Set(
      new Book("Programming in Scala"),
      new Book("Walden")
    )

  def printBookList(info: Book => AnyRef) {  // requires function from Book to AnyRef
    for (book <- books) println(info(book))  // always passes a Book to a function
  }
}

object Customer extends Application {
  def getTitle(p: Publication): String = p.title  // accesses only Publication
  Library.printBookList(getTitle)  // provides function from Publication to String
}
// any method declared in Publication is also available on its subclass Book
// Publication => String is a subtype of Book => AnyRef
```

![Map hierarchy](https://github.com/mbonaci/scala/blob/master/resources/Scala-covariance-contravariance.png?raw=true)

> - because the result type of a `Function1` is defined as _covariant_, the inheritance
relationship of the two result types, shown at the right of the image, is in the same direction as that of the two functions shown in the center
> - because the parameter type of a `Function1` is defined as _contravariant_, the inheritance relationship of the two parameter types, shown at the left of the image, is in the opposite direction as that of the two functions

* 441 - **Object private data**

> - object or class components that are declared as `private[this]`
> - may be accessed only from within their containing object, in which they are defined
> - accesses to vars from the same object do not cause problems with variance
> - variance rules can be broken by having a reference to a containing object that has a statically weaker type than the type the object was defined with
> - for object private values, this is not possible
> - variance checking has a special case for object private definitions, which is that such definitions are omitted when checking correctness of variance positions

```scala
/*
 * Purely functional Queue that performs at most one trailing
 * to leading adjustment for any sequence of head operations
 * Yes, it has reassignable fields, but they are private,
 * thus invisible to any client using the class
 */
class CovariantQueue[+T] private (
    private[this] var leading: List[T],  // object private vars
    private[this] var trailing: List[T]
    // without [this]:
    // error: covariant type T occurs in contravariant position
    // in type List[T] of parameter of setter leading_=
  ) {
  private def mirror() =
    if (leading.isEmpty) {
      while (!trailing.isEmpty) {
        leading = trailing.head :: leading
        trailing = trailing.tail
      }
    }
  
  def head: T = {
    mirror()
    leading.head
  }
  
  def tail: CovariantQueue[T] = {
    mirror()
    new CovariantQueue(leading.tail, trailing)
  }
  
  def enqueue[U >: T](x: U) =
    new CovariantQueue[U](leading, x :: trailing)

}
```

* 443 - **Upper bounds**

> - with the `T <: Ordered[T]` you indicate that the type parameter `T` has an upper bound `Ordered[T]`, which means that the passed element's type must be a subtype of `Ordered`
> - used e.g. to require that the passed type mixes in a trait (i.e. is a subtype of trait)

```scala
// requires that passed list type mixes in Ordered trait
def orderedMergeSort[T <: Ordered[T]](xs: List[T]): List[T] = {
  def merge(xs: List[T], ys: List[T]): List[T] =
    (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xs1, y :: ys1) =>
        if (x < y) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
    }
  val n = xs.length / 2
  if (n == 0) xs
  else {
    val (ys, zs) = xs splitAt n
    merge(orderedMergeSort(ys), orderedMergeSort(zs))
  }
}

// this is not a most general way to implement mergeSort, since you cannot pass e.g. List[Int]
// that's achieved with 'implicit parameters' and 'view bounds' (section 21.6)
```

### Abstract Members

> - a member of a class or trait is `abstract` if the member does not have a complete definition in the class
> - abstract members are intended to be implemented by subclasses
> - in Scala, besides methods, you can declare abstract fields and even abstract types as members of classes and traits (vals, vars, methods and types)
> - a concrete implementation needs to fill in definitions for each of its abstract members:

```scala
// declaration of all four types of abstract members
trait Abstract {
  type T
  def transform(x: T): T
  val initial: T
  var current: T
}

// the concrete implementation of four type of abstract members
class Concrete extends Abstract {
  type T = String  // defines type 'T' as an alias of type 'String'
  def transform(x: String) = x + x
  val initial = "hi"
  var current = initial
}
```

* 448 - **Type members**

> - **abstract types** are always members of some class or trait
> - traits are abstract by definition
> - a **non-abstract type member** is a way to define a new name (alias) for a type
> - one reason to use a type member is to define a short, descriptive alias for a type whose real name is more verbose or less obvious in meaning (helps clarify the code)
> - the other main use of type members is to declare abstract types that must be defined in subclasses

* 449 - **Abstract vals**

> - have a form like `val initial: String`
> - `val` is given a name and a type, but not its value
> - use it when you know that each instance of the class will have an unchangeable value, but you don't know what that value will be
> - its concrete implementation must be a `val` (may not be `var` or `def`)
> - guaranteed to return always the same value, unlike methods, which could be implemented by a concrete method that returns a different value every time it's called
> - **abstract method declarations** may be implemented by both, concrete method and concrete `val` definitions

* 450 - **Abstract vars**

> - implicitly declare abstract getters and setters, just like non-abstract `vars` do
> - reassignable field is not created

```scala
trait AbstractTime {
  var hour: Int
  var minute: Int
}

// gets expanded to:
trait AbstractTime {
  def hour: Int
  def hour_=(x: Int)
  def minute: Int
  def minute_=(x: Int)
}
```

* 451 - **Initializing abstract vals**

> - abstract vals sometimes play a role of superclass parameters, i.e. they let you provide details in a subclass that are missing in a superclass
> - that is particularly important for _traits_, because they don't have a constructor to which you could pass parameters

```scala
// instead of class with two class parameters
trait RationalTrait {
  val numerArg: Int  // 0 until mixed in
  val denomArg: Int
}

def main(args: Array[String]): Unit = {
  // example implementation of two abstract vals
  // yields an instance of an anonymous class
  // which mixes in the trait and is defined by the body
  new RationalTrait {
    val numerArg = 1  // set to 1 as part of the initialization of the anonymous class
    val denomArg = 2  // but the anonymous class is initialized after the RationalTrait
  }                   // in the meantime, vals are set to their type's default values
}
```

> - a class parameter argument is evaluated **before** it is passed to the class constructor (unless it's a by-name parameter)
> - an implementing `val` definition in a subclass is evaluated only **after** the superclass has been initialized
> - e.g. class parameters of `Rational(expr1, expr2)` are evaluated just before instantiation of the `Rational` object, but `RationalTrait`'s vals are evaluated as part of the initialization of the **anonymous class**

```scala
trait ProblematicRationalTrait {
  val numerArg: Int  // initialized once an anonymous class is created
  val denomArg: Int  // which happens after the trait is initialized
  require(denomArg != 0)  // throws "requirement failed" exception
  private val g = gcd(numerArg, denomArg)
  val numer = numerArg / g
  val denom = denomArg / g
  
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a
    else gcd(b, a % b)
  
  override def toString = numer + "/" + denom
}

// when you execute this, 'require' fails, since Int vals are 0 until 
val x = 2
val fun = new ProblematicRationalTrait {
  val numerArg = 1 * x
  val denomArg = 2 * x
}
```

* 453 - **Pre-initialized fields**

> - let you initialize a field of a subclass before the superclass is called
> - achieved by putting field definition in braces before superclass constructor call:

```scala
// anonymous class creation:
new {
  val numerArg = 1 * x
  val denomArg = 2 * x
} with ProblematicRationalTrait

// object definition:
object twoThirds extends {
  val numerArg = 2
  val denomArg = 3
} with ProblematicRationalTrait

// subclass definition:
class RationalClass(n: Int, d: Int) extends {
  val numerArg = n
  val denomArg = d
} with ProblematicRationalTrait {
  def + (that: RationalClass) = new RationalClass(
    numer * that.denom + that.numer * denom,
    denom * that.denom
  )
}
// in all cases initialization section comes before the trait is mentioned
```

> - because pre-initialized fields are initialized before the superclass constructor is called, their initializers cannot refer to the object that's being constructed
> - so, if such an object refers to `this`, the reference goes to the object containing the class or object that's being constructed, not the constructed object itself:

```scala
object AbsRat {
  // ...
  val rat = new {
    val numerArg = 1
    val denomArg = this.numerArg * 2  // value numerArg is not member of object AbsRat
  } with ProblematicRationalTrait
  // ...
```

* 455 - **Lazy vals**

> - evaluated the first time the val is used
> - never evaluated more than once (the result of first time evaluation is stored in val)
> - Scala objects are also initialized on demand, in fact an object definition can be thought of as a shorthand definition of a lazy val with an anonymous class that describes the object's contents
> - since lazy vals get executed on demand, their textual order is not important when determining the order of their initialization
> - in the presence of side effects (i.e. when our code produces or is affected by mutations), initialization order starts to matter and then it can be difficult to determine the actual order, which is why lazy vals are an ideal complement to functional objects

```scala
trait LazyRationalTrait {
  val numerArg: Int
  val denomArg: Int
  lazy val numer = numerArg / g
  lazy val denom = denomArg / g
  override def toString = numer + "/" + denom
  
  private lazy val g = {
    require(denomArg != 0)
    gcd(numerArg, denomArg)
  }
  
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)
}

// using LazyRationalTrait from the interpreter:
scala> val x = 2
scala> new LazyRationalTrait {
     | val numerArg = 1 * x
     | val denomArg = 2 * x
     | }
res2: LazyRationalTrait = 1/2

// 1. - fresh instance of LazyRationalTrait gets created and the initialization code
//      of LazyRationalTrait is run (fields are not initialized)
// 2. - the primary constructor of the anonymous subclass is executed (expression 'new')
//    - this involves the initialization of 'numerArg' with 2 and 'denomArg' with 4
// 3. - the 'toString' method is invoked on the constructed object (by the interpreter)
// 4. - the 'numer' field is accessed for the first time, by the 'toString' method
// 5. - the initializer of 'numer' accesses the private field 'g', so 'g' is evaluated
//    - the evaluation of 'g' accesses 'numerArg' and 'denomArg' (from step 2)
// 6. - the 'toString' method accesses the value of 'denom', which causes its evaluation
//    - that evaluation accesses the values of 'denomArg' and 'g'
//    - the initializer of the 'g' field is not re-evaluated (it's a 'val', not 'def')
// 7. - finally, the resulting string "1/2" is constructed and printed
```

* 459 - **Abstract types**

> - used as a placeholder for a type that will be defined further down the hierarchy

```scala
// the type of food cannot be determined at the 'Animal' level, every subclass defines it
class Food
abstract class Animal {
  type SuitableFood <: Food  // upper bound is 'Food' (requires subclass of 'Food')
  def eat(food: SuitableFood)
}

class Grass extends Food
class Cow extends Animal {
  type SuitableFood = Grass  // 'Cow' fixes its 'SuitableFood' to be 'Grass'
                             // 'SuitableFood' becomes alias for class 'Grass'
  override def eat(food: Grass) {}  // concrete method for this kind of 'Food'
}
```

* 461 - **Path-dependent types**

> - objects in Scala can have types as members (e.g. any instance of 'Cow' will have type 'SuitableFood' as its member)
> - e.g. `milka.SuitableFood` means "the type 'SuitableFood' that is a member of the object referenced from 'milka'", or "the type of 'Food' that suitable for 'milka'"
> - a type like `milka.SuitableFood` is called a **path-dependent type**, where the word "path" means "the reference to an object"
> - **path** can be a single name, such as 'milka', or a longer access path, like `farm.barn.milka.SuitableFood`, where path components are variables (or singleton object names)that refer to objects

```scala
class DogFood extends Food
class Dog extends Animal {
  type SuitableFood = DogFood
  override def eat(food: DogFood) {}
}

val milka = new Cow
val lassie = new Dog
lassie eat (new milka.SuitableFood)  // error: type mismatch; found: Grass, required: DogFood

// 'SuitableFood' types of two 'Dog's both point to the same type, 'DogFood'
val mickey = new Dog
lassie eat (new mickey.SuitableFood)  // OK
```

> - although path-dependent types resemble Java's inner classes, there is a crucial difference:
>   - a path-dependent type names an outer **object**, whereas an inner class type name an outer class

```scala
class Outer {
  class Inner
}

// the inner class is addressed 'Outer#Inner', instead of Java's 'Outer.Inner'
// in Scala, '.' notation syntax is reserved for objects

val out1 = new Outer
val out2 = new Outer

out1.Inner  // path-dependent type
out2.Inner  // path-dependent type (different one)
// both types are subtypes of 'Outer#Inner', which represents the 'Inner' class with an
// arbitrary outer object of type 'Outer'
// by contrast, 'out1.Inner' refers to the 'Inner' class with a specific outer object
// likewise, type 'out2.Inner' refers to the 'Inner' class with a different, specific
// outer object (the one referenced from 'out2')
```

> - the same as in Java, inner class instances hold a reference to an enclosing outer class instance, which allows an inner class to access members of its outer class
> - thus, you cannot instantiate inner class without in some way specifying outer class instance
>   - one way to do this is to instantiate the inner class inside the body of the outer class (in this case, the current outer class instance is used - 'this')
>   - the other way is to use a path-dependent type:

```scala
new out1.Inner  // since 'out1' is a reference to a specific outer object
```

> - the resulting inner object will contain a reference to its outer object ('out1')
> - by contrast, because the type `Outer#Inner` does not name any specific instance of `Outer`, you can't instantiate it:

```scala
new Outer#Inner  // error: Outer is not a legal prefix for a constructor
```

* 464 - **Structural subtyping with Refinement types**

> - when one class inherits from the other, the first one is said to be a **nominal subtype** of the other one (explicit subtype, by name)
> - Scala additionally supports **structural subtyping**, where you get a subtyping relationship simply because two types have the same members
> - _structural subtyping_ is expressed using **refinement types**
> - it is recommended that the _nominal subtyping_ is used wherever it can, because _structural subtyping_ can be more flexible than needed (e.g. a Graph and a Cowboy can `draw()`, but you'd rather get a compilation error than call graphical draw on cowboy)

```scala
// sometimes there is no more to a type than its members
// e.g. suppose you wanted to define 'Pasture' class that can contain animals that eat 'Grass'
// one could define a trait 'AnimalThatEatsGrass' and mix it in classes, where applicable
// but that would be verbose, since 'Cow' already declares that it's an animal that eats grass
// and with the trait, it again declares that it's an 'AnimalThatEatsGrass'
// instead, you can use a 'refinement type', and to do that
// you write the base type, Animal, followed by a sequence of members in curly braces
// which are used to further refine the types of members from the base class
// so here is how to write the type "animal that eats grass":
Animal { type SuitableFood = Grass }

// and given this type, you can write the pasture:
class Pasture {
  var animals: List[Animal { type SuitableFood = Grass }] = Nil
  // ...
}
```

> - another application of structural subtyping is grouping together a number of classes that were written by someone else
> - for example, to generalize [the loan pattern from page 216](#control-abstractions), which worked for only for type `PrintWriter`, to work with any type with a `close` method:

```scala
// the first try:
def using[T, S](obj: T)(operation: T => S) = {  // operation from any to any type
  val result = operation(obj)
  obj.close() // type error: 'T' can be any type and 'AnyRef' doesn't have 'close' method
  result
}

// the proper implementation:
def using[T <: { def close(): Unit}, S](obj: T)(operation: T => S) = {
  // upper bound of 'T' is the structural type '{def close(): Unit}'
  // which means: "any subtype of AnyRef that has a 'close' method"
  val result = operation(obj)
  obj.close()
  result
}
```

> - **Structural type** is a refinement type where the refinements are for members that are not in the base type

* 466 - **Enumerations**

> - Scala's _enumerations_ are not a built-in construct
> - defined in `scala.Enumaration`
> - to create a new enumeration, you define an object that extends `scala.Enumeration`

```scala
object Color extends Enumeration {
  val Red, Green, Blue = Value  // 'Value' is an inner class of 'Enumeration'
}
// 'Value' is also a method of Enumeration that returns a new instance of 'Value' class
// so 'Color.Red' is of type 'Color.Value' and so is any other enum value in object Color

// Color object definition provides 3 values: 'Color.Red', 'Color.Green' and 'Color.Blue'

// to import everything from Color:
import Color._  // and then just use 'Red', 'Green' and 'Blue', without the object name
```

> - `Color.Value` is a _path-dependent_ type, with `Color` being the path and `Value` being the dependent type
> - it's a completely new type, different from all other types
> - you can associate names with enumeration values by using a different overloaded variant of the `Value` method:

```scala
object Direction extends Enumeration {
  val Left = Value("Left")
  val Right = Value("Right")
}

// to iterate over values:
for (d <- Direction.values) print(d + " ")  // Left Right

// you can get the number of enumeration value by its 'id' method (zero-based):
Direction.Right.id  // Int = 1

// also:
Direction(0)  // Direction.Value = Left
```

### Implicit Conversions and Parameters

> - used when working with two bodies of code that were developed separately, thus each may have its own way to represent the same concept
> - **implicits** help by reducing the number of explicit conversions one has to write

```scala
// using Swing without implicit conversions (a lot of boilerplate):
val button = new JButton
button.addActionListener(
  new ActionListener {
    def actionPerformed(event: ActionEvent) {
      println("pressed!")
    }
  }
)

// Scala-friendly version:
button.addActionListener(  // type mismatch (passing function instead of ActionListener)
  (_: ActionEvent) => println("pressed!")
)

// first step is to define implicit conversion from function to action listener:
implicit def function2ActionListener(f: ActionEvent => Unit) =
  new ActionListener {
    def actionPerformed(event: ActionEvent) = f(event)
  }

// now we can write:
button.addActionListener(
  function2ActionListener(
    (_: ActionEvent) => println("pressed!")
  )
)

// which is already much better than the inner class version
// because the function 'function2ActionListener' is marked as 'implicit', it can be left out:
button.AddActionListener(  // now this works!
  (_: ActionEvent) => println("pressed!")
)
```

> - how implicits work:
>   - the compiler first tries to compile it as is, but it sees a type error
>   - it looks for an implicit conversion that can repair the problem
>   - it finds 'function2ActionListener'
>   - it tries to use it as a conversion method, sees that it works and moves on

* 482 - **Rules for implicits**

> - **implicit definitions** are definitions that the compiler is allowed to insert into a program in order to fix a type error
> - you can use `implicit` to mark any variable, function or object definition  
> 
> _Implicit conversions are governed by the following general rules_
> - **Marking rule:** Only definitions marked `implicit` are used
> - **Scope rule:** An inserted implicit conversion must be in scope as a single identifier, or be associated with the conversion's source or target type
>   - _single identifier_ means that the compiler will not insert a conversion of the form `someVariable.convert`
>   - it is common for libraries to include a `Preamble` object that contains useful implicits, which allows the code that uses a library to do a single `import Preamble._`
>   - there's one exception to single identifier rule: the compiler will also look for implicit definitions in the companion objects of both, source and target types
>   - when implicit is placed in a companion object of some type, it is said that the conversion is **associated** to that type
>   - _modular code reasoning:_ when you read a source file, the only things you need to consider in other source files are those that are either imported or explicitly referenced through a fully qualified name
> - **One-at-a-time rule: Only one implicit is tried**
>   - for sanity's sake, the compiler does not insert further implicits when it's already in the process of trying another implicit, e.g. `convert1(convert2(x)) + y`
>   - that would cause compile time to increase dramatically on erroneous code and would increase the difference between what the programmer writes and what the program does
>   - it is possible to circumvent this rule by having implicits take implicit params
> - **Explicits-First rule:**
>   - the compiler will not change code that already works
>   - a consequence of this rule is that you can make trade offs between verbose (explicits) and terse (implicits) code

* 484 - **Naming an implicit conversion**

> - implicit conversions can have arbitrary names
> - the name matters only in two situations:
>   - if you want to write it explicitly in a method application
>   - for determining which implicits are available in a program

```scala
// to determine which implicits will be used:
object MyConversions {
  implicit def stringWrapper(s: String): IndexedSeq[Char] = ...
  implicit def intToString(x: Int): String = ...
}

// you can achieve that your code uses only 'stringWrapper' and not 'intToString':
import MyConversions.stringWrapper  // possible only because implicit has a name
// ... code making use of 'stringWrapper'
```

* 485 - **Where implicits are tried**

> - there are 3 places where implicits are used:  
> **1**  conversions to an expected type (use one type where the other is expected)  
> **2**  conversions of the receiver of a selection (adapts receiver of a method call)  
> **3**  implicit parameters

* 485 - **Implicit conversion to an expected type**

> - whenever the compiler sees an `X`, but needs a `Y`, it will look for an implicit function that converts `X` to `Y`

```scala
val i: Int = 3.5  // type mismatch (loss of precision)

// however, if you define implicit conversion:
implicit def doubleToInt(x: Double) = x.toInt

val i: Int = 3.5  // i: Int = 3

/*
 * 1. the compiler sees a 'Double' 3.5 in a context where it requires an 'Int'
 * 2. before giving up and reporting 'type mismatch', it searches for a suitable implicit
 * 3. it finds 'doubleToInt', and wraps 3.5 in the 'doubleToInt' function call
 */
```

* 486 - **Converting the receiver**

> - implicits are applied on an object on which a method is invoked
> - has 2 main uses: allows smoother integration of a new class into an existing class hierarchy and second, they support writing DSLs withing the Scala language
> - how it works:
>   - you write down `obj.doIt` where `obj` doesn't have a member named `doIt`
>   - before giving up, compiler tries to insert conversions that apply to the `obj`, converting it to a type that has a `doIt` member

```scala
// use an instance of one type as if it was an instance of some other type
class Rational(n: Int, d: Int) {  // existing class (from page 155)
  // ...
  def + (that: Rational): Rational = ...
  def + (that: Int): Rational = ...
}

// the two overloaded methods allow you to:
val oneHalf = new Rational(1, 2)  // Rational = 1/2
oneHalf + oneHalf  // Rational = 1/1
oneHalf + 1  // Rational = 3/2

// but:
1 + oneHalf  // error: overloaded method value + (of Int) cannot bi applied to Rational

// so:
implicit def intToRational(x: Int) = new Rational(x, 1)

1 + oneHalf  // Rational = 3/2

/*
 * 1. the compiler first tries to type check the expression '1 + oneHalf' as it is
 * 2. this fails because none of Int's '+' methods takes a 'Rational' argument
 * 3. compiler searches for an implicit conversion from 'Int' to another type 
 *    that has a '+' method which can be applied to a 'Rational'
 * 4. it finds 'intToRational' and wraps 1 in the 'intToRational' call:
 */
intToRational(1) + oneHalf
```

* 489 - **Simulating new syntax**

> - the major use of implicit conversions is to simulate adding new syntax

```scala
// you can make a map using syntax:
Map(1 -> "one", 2 -> "two")  // what is '->'

// '->' is a method of the class 'ArrowAssoc' (defined in 'scala.Predef' preamble)
// preamble also defines an implicit conversion from 'Any' to 'ArrayAssoc' so that the
// '->' method can be found:

package scala
object Predef {
  class ArrowAssoc[A](x: A) {
    def -> [B](y: B): Tuple2[A, B] = Tuple2(x, y)
  }

  implicit def any2ArrowAssoc[A](x: A): ArrowAssoc[A] = new ArrowAssoc(x)

  // ...
}
```

> - that is called a **rich wrapper pattern**, which is common in libraries that provide syntax-like extensions to the language
> - classes named 'RichSomething' (e.g. 'RichInt' or 'RichBoolean') are likely using implicits to add the syntax-like methods to type 'Something'

* 489 - **Implicit parameters**

> - compiler can also insert implicits within argument lists, e.g. replacing `someCall(a)` with `someCall(a)(b)` or `new SomeClass(a)` with `new SomeClass(a)(b)`, thereby adding a missing parameter list to complete a function call
> - it is the entire last curried parameter that's supplied, not just the last parameter, e.g. compiler might replace `aCall(a)` with `aCall(a)(b, c, d)`
> - for this to work, not just that the inserted identifiers (such as b, c and d) must be marked `implicit` where they are defined, but also the last parameter list in `aCall`'s definition must be marked `implicit`:

```scala
// suppose you have a class which encapsulates a user's preferred shell prompt string:
class PreferredPrompt(val preference: String)

object Greeter {
  def greet
    (name: String)  // first param list
    (implicit prompt: PreferredPrompt) {  // implicit applies to the entire param list
      println("Welcome, " + name)
      println(prompt.preference)
  }
}

object Prompt {  // dummy - just hosting 'main'
  def main(args: Array[String]): Unit = {
    val bobsPrompt = new PreferredPrompt("relax> ")
    Greeter.greet("Bob")(bobsPrompt)  // explicit prompt
    
    implicit val prompt = new PreferredPrompt("Yes, master> ")  // implicit identifier
    Greeter.greet("Joe")  // implicit prompt
  }
}
```

> - example with multiple parameters in the last parameter list:

```scala
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

object Prompt {  // dummy - just hosting 'main'
  def main(args: Array[String]): Unit = {
    val bobsPrompt = new PreferredPrompt("relax> ")
    val bobsDrink = new PreferredDrink("travarica")
    Greeter.greet("Bob")(bobsPrompt, bobsDrink)  // all explicit
    
    implicit val prompt = new PreferredPrompt("Yes, master> ")
    implicit val drink = new PreferredDrink("rakija")
    Greeter.greet("Joe")
  }
}
```

> - implicit parameters are most often used to provide information about a type mentioned explicitly in the earlier parameter list (like _type classes_ in Haskell):

```scala
// the weakness of this method is that you cannot use it to sort list of Ints
// because it requires that 'T' is a subtype of 'Ordered[T]', which Int isn't
def maxListUpBound[T <: Ordered[T]](elements: List[T]): T =
  elements match {
    case List() => throw new IllegalArgumentException("empty")
    case List(x) => x
    case x :: rest =>
      val maxRest = maxListUpBound(rest)
      if (x > maxRest) x
      else maxRest
  }

// to remedy the weakness, we could add an extra argument
// that converts 'T' to 'Ordered[T]' (i.e. provides info on how to order 'T's)
def maxListImpParm[T](elements: List[T])(implicit ordered: T => Ordered[T]): T =
  elements match {
    case List() => throw new IllegalArgumentException("empty")
    case List(x) => x
    case x :: rest =>
      val maxRest = maxListImpParm(rest)(ordered)
      if (ordered(x) > maxRest) x
      else maxRest
  }

// because patter is so common, the standard library provides implicit 'orderer'
// methods for many common types, which is why you can use 'maxListImpParm' with:
maxListImpParm(List(1, 5, 10, 3))  // compiler inserts 'orderer' function for Ints
maxListImpParm(List(1.5, 5.2, 10.7, 3.22323))  // for Doubles
maxListImpParm(List("one", "two", "three"))    // for String

/*
 * Because elements must always be provided explicitly in any invocation of
 * maxListImpParm, the compiler will know T at compile time, and can therefore
 * determine whether an implicit definition of type T => Ordered[T] is in
 * scope. If so, it can pass in the second parameter list, 'orderer', implicitly.
 */
```

* 495 - **A style rule for implicit parameters**

> - it is best to use a custom named type in the types of implicit parameters (e.g. in the `Prompt` example, the type of `prompt` and `drink` was not `String`, but `PreferredPrompt` and `PreferredDrink`)
> - use at least one role-determining name within the type of an implicit parameter (in our case `Ordered`)

* 495 - **View bounds**

> - when you use `implicit` on a parameter, then not only will the compiler try to supply that parameter with an implicit value, but it will also use that parameter as an available implicit in the body of the method:

```scala
def maxList[T](elements: List[T])(implicit orderer: T => Ordered[T]): T =
  elements match {
    case List() => throw new IllegalArgumentException("empty")
    case List(x) => x
    case x :: rest =>
      val maxRest = maxList(rest)  // '(orderer)' is implicit
      if (x > maxRest) x           // 'orderer(x)' is implicit
      else maxRest
  }

/*
 * 1. compiler sees that types don't match (e.g. 'x' of type 'T' doesn't have '>' method)
 * 2. compiler looks for implicit conversions to repair the code
 * 3. it finds 'orderer' in scope and converts the code to 'orderer(x) > maxRest'
 * 4. it also converts 'maxList(rest)' to 'maxList(rest)(orderer)'
 * 5. after these two implicit insertions the code fully type checks
 *
 * All that happens without a single mention of the 'orderer' parameter in the body, thus
 * all uses of 'orderer' are implicit
 */
```

> - because this pattern is so common, Scala lets you leave out the name of this parameter and shorten the method header by using a **view bound:**

```scala
def maxList[T <% Ordered[T]](elements: List[T]): T =
  elements match {
    case List() => throw new IllegalArgumentException("empty")
    case List(x) => x
    case x :: rest =>
      val maxRest = maxList(rest)  // '(orderer)' is implicit
      if (x > maxRest) x           // 'orderer(x)' is implicit
      else maxRest
  }
```

> - you can think of `T <% Ordered[T]` as saying "I can use any T, so long as T can be treated as an Ordered[T]", which is different from "T is an Ordered[T]", as **upper bound**, `T <: Ordered[T]`, would say
> - so even though class `Int` is not a subtype of `Ordered[Int]`, we can still pass a `List[Int]` to `maxList`, so long as an implicit conversion from `Int` to `Ordered[Int]` is available
> - if type `T` happens to already be an `Ordered[T]`, you can still pass a `List[T]` to `maxList` because the compiler will use an implicit **identity function**, declared in `Predef`:

```scala
implicit def identity[A](x: A): A = x  // simply returns received object
```

* 498 - **When multiple conversions apply**

> - when multiple implicit conversions are in scope, compiler chooses the most specific one (e.g. if one of the conversions takes `String` and the other takes `Any`, the compiler will choose the one that takes a `String`)
> - one implicit conversion is **more specific** than the other if one of the following applies:
>   - the argument type of the former is a subtype of the latter's
>   - both conversions are methods and the enclosing class of the former extends the enclosing class of the latter one

* 501 - **Debugging implicits**

> - when you wonder why the compiler did not find an implicit conversion that you think should have been applied, it helps to write the conversion explicitly, which would possibly produce an error message so you'll know the reason why it was not applied
> - if inserting the conversion explicitly make the error go away, then you know that insertion was prevented by one of the rules (often Scope rule)
> - `-Xprint:typer` option tells the compiler to show what the code looks like after all implicit conversions have been added by the type checker
> - implicits can make code confusing if used too frequently, thus, before writing a new implicit conversion, first try to achieve the same effect using inheritance, mixin composition or method overloading

### Implementing Lists
* 503 - **The List class in principle**

> - lists are not built-in as a language construct in Scala, they are defined by an abstract class `scala.List`, which comes with 2 subclasses, `Nil` and `::`

![Lists hierarchy](https://github.com/mbonaci/scala/blob/master/resources/Scala-Lists-hierarchy.png?raw=true)

```scala
package scala
abstract class List[+T] {  // you can assign 'List[Int]' to var of type 'List[Any]'

// 3 main methods are abstract in class 'List', and concrete in classes 'Nil' and '::'
def isEmpty: Boolean
def head: T
def tail: List[T]
```

