Java developer's Scala cheat sheet
-----

* 252 - Scala type hierarchy:
![Scala class hierarchy image](http://i1329.photobucket.com/albums/w548/mbonaci/scala-class-hierarchy_zpsfc2e1b03.jpg)

* 127 - The convention is to include empty parentheses when invoking a method only if that method has side effects. 226 - **Pure methods** are methods that don't have any side effects and don't depend on mutable state. Simply, if the function you’re calling performs an operation, use the parentheses, but if it merely provides access to a property, leave out the parentheses
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
* 141 - **Class parameters**: Any code placed in the class body (outside methods) will be placed in the *primary constructor*
* 143 - A **precondition** is a constraint on values passed into a method or constructor (E.g. `require(d != 0)` in the class body will throw `IllegalArgumentException: requirement failed` when `0` is passed as `d`)
* 144 - If **Class parameters** are only used inside constructors, the Scala compiler will not create corresponding fields for them
* 146 - **Auxiliary constructors** - constructors other than the primary constructor
* 147 - Every *auxiliary constructor* must invoke another constructor **of the same class** (like Java, only Java can also call superclass's constructor instead) as its first action. That other constructor must textually come before the calling constructor
* 152 - The convention is to use camel case for constants, such as `XOffset`
* 153 - The Scala compiler will internally “mangle” operator identifiers to turn them into legal Java identifiers with embedded `$` characters. For instance, the identifier `:->` would be represented internally as `$colon$minus$greater`. If you ever wanted to access this identifier from Java code, you’d need to use this internal representation.
* 153 - A **mixed identifier** consists of an alphanumeric identifier, which is followed by an underscore and an operator identifier. For example, `unary_+`. They are used to support *properties* 
* 153 - A **literal identifier** is an arbitrary string enclosed in back ticks
* 156 - **Implicit conversion** definition:

```scala
implicit def intToRational(x: Int) = new Rational(x)
```
for an implicit conversion to work, it needs to be in scope. If you place the implicit method definition inside the class Rational, it won’t be in scope in the interpreter

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
      trimmed = line.trim
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
* 186 - **Local functions** are functions inside other functions. They are visible only in their enclosing block
* 188 - **Function literal** example: `(x: Int) => x + 1`
* 188 - Every function value is an instance of some class that extends one of `FunctionN` traits that has an `apply` method used to invoke the function (`Function0` for functions with no params, `Function1` for functions with 1 param, ...)
* 189 - **foreach** is a method of `Traversable` trait (supertrait of `List`, `Set`, `Array` and `Map`) which takes a function as an argument and applies it on all elements
* 190 - **filter** method takes a function that maps each element to true or false, e.g. `someNums.filter((x: Int) => x > 0)`
* 190 - **Target typing** - Scala infers type by examining the way the expression is used, e.g. `filter` example can be written: `someNums.filter(x => x > 0)`
* 191 - **Placeholder** allow you to write: `someNums.filter(_ > 0)`, but only if each function parameter appears in function literal only once (one underscore for each param, sequentialy).
Sometimes the compiler might not have enough info to infer missing param types:

```scala
val f = _ + _  // error: missing parameter type for expanded function...
val f = (_: Int) + (_: Int)  // OK: f(5, 10) = 15
```

* 192 - **Partially applied function (PAF)** is an expression in which you don’t supply all of the arguments needed by the function. Instead, you supply some, or none:

```scala
someNums.foreach(println _)  
// is equivalent to:
someNums.foreach(x => println(x))
// if a function value is required in that place you can ommit the placeholder:
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
Because of the JVM instruction set, tail call optimization cannot be applied for two mutually recursive functions nor if the final call goes to a function value (function wraps the recursive call):

```scala
val funValue = nestedFun _
def nestedFun(x: Int) {
  if (x != 0) {println(x); funValue(x - 1)}  // won't be optimized
}
```

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

* 218 - **By-name parameters** Typically, parameters to functions are *by-value* parameters, meaning, the value of the parameter is determined before it is passed to the function. But if you need to write a function that accepts as a parameter an expression that you don't want evaluated until it's called within your function? For this circumstance, Scala offers **call-by-name parameters**. A *call-by-name* mechanism passes a code block to the callee and each time the callee accesses the parameter, the code block is executed and the value is calculated:

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

* 222 - **Composition** means one class holds a reference to another
* 224 - A method is **abstract** if it does not have an implementation (i.e., no equals sign or body). Unlike Java, no abstract modifier is allowed on method declarations. Methods that do have an implementation are called **concrete**.
* 224 - Class is said to **declare an abstract method** and that it **defines a concrete method** (i.e. *declaration* is *abstract*, *definition* is *concrete*)
* 225 - Methods with empty parentheses are called **empty-paren methods**. This convention (see bullet 127 on top) supports the *uniform access principle*, which says that the client code should not be affected by a decision to implement an attribute as a field or as a method (from the client code perspective, it should be irrelevant whether `val` or `def` is accessed. The only difference is speed, since fields are pre-computed when the class is initialized)
* 229 - Fields and methods belong to the same *namespace*, which makes possible for a
field to override a parameterless method, but it forbids defining a field and a method with the same name 
* 230 - *Java* has four namespaces: fields, methods, types and packages
        *Scala* has two namespaces:
          **values** (fields, methods, packages and sigleton objects)
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

* 238 - If you want to disallow for a method to be overriden or for a class to be subclassed, use the keyword **final** (e.g. `final class ...` or `final def ...`)
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

* 250 - In Scala hierarchy, **Null** and **Nothing** are the subclasses of every class, just as **Any** is the superclass of every other class
* 250 - `Any` contains methods:

> `==`..........`final`, same as `equals` (except for Java boxed numeric types)  
> `!=`..........`final`, same as `!equals`  
> `equals`....used by the subclasses to override equality  
> `##`...........same as `hashCode`  
> `hashCode`  
> `toString`  

* 251 - Class `Any` has two subclasses:

> `AnyVal`      the parent class of every built-in **value class** in Scala  
> `AnyRef`
> 
> Built-in **value classes**: `Byte`, `Short`, `Char`, `Int`, `Long`, `Float`, `Double`, `Boolean` and `Unit`
>  - represented (except `Unit`) as Java primitives at runtime
>  - both `abstract` and `final`, so you cannot instantiate them with `new`
>  - the instances of these classes are all written as literals (e.g. `5` is `Int`) 
>  - `Unit` corresponds to Java's `void` and has a single instance value, `()`

