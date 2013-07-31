package scala2e.chapter09

import java.io.File

object PrintWriterHelper {

  def main(args: Array[String]): Unit = {
    withPrintWriter(
      new File("date.txt"), 
      writer => writer.println(new java.util.Date))
  }

  def withPrintWriter(file: File, op: java.io.PrintWriter => Unit) {
    val writer = new java.io.PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }
}