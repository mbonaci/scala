package scala2e.chapter07

class GeneratorsAndFilters {
  val files = (new java.io.File("src")).listFiles
  
  def fileLines(file: java.io.File) =
    scala.io.Source.fromFile(file).getLines().toList

  def grep(pattern: String) = {
    for{
      file <- files
      if file.getName().endsWith(".scala") || file.getName().endsWith(".sc")
      line <- fileLines(file)
      trimmed = line.trim()
      if trimmed.matches(pattern)
    } yield trimmed 
 
  }
    
  def scalaFiles = {
    for (
      file <- files
      if file.getName.endsWith(".scala")
    ) yield file
  }
    
}