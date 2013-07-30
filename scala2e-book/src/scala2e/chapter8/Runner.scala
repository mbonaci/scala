package scala2e.chapter8

/**
 * application expects the line width as the first command-line argument, 
 * and interprets subsequent arguments as filenames
 */
object Runner extends App {

  //val files = (new java.io.File("src")).listFiles
  //val paths = for(file <- files) yield file.getPath()
  
  val width = args(0)
  for(arg <- args.drop(1))
	  LongLines.procesFileFunctional(width.toInt, arg)
}