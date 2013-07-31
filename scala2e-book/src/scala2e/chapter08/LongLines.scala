package scala2e.chapter8
import scala.io.Source

object LongLines {

  /*
   * The processFile method creates a Source object from the file name
   * and, in the generator of the for expression, calls getLines on the source.
   * Then it calls processLine method for each line it reads.
   */
  def processFile(width: Int, filename: String) {
    val source = Source.fromFile(filename);
    for(line: String <- source.getLines())
      processLine(filename, width, line)
  }
  
  /*
   * The processLine method tests whether the length of the line is 
   * greater than the given width, and, if so, it prints 
   * the filename, a colon, and the trimmed line.
   */
  private def processLine(path: String, width: Int, line: String) {
    if(line.length > width)
      println(path + ": " + line.trim)
  }
  
  /**
   * Refactored processFile that contains local function
   */
  def procesFileFunctional(width: Int, filename: String) {
    def processLine(line: String) {
      if(line.length > width)
        println(filename + ": " + line.trim)
    }
    
    val source = Source.fromFile(filename);
    for(line: String <- source.getLines())
      processLine(line)
  }
    
}