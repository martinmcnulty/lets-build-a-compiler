package uk.me.mcnulty.lbac

object Cradle {

  def error(s: String): Unit = {
    println();
    println(s"Error: $s.")
  }

  def abort(s: String): Nothing = {
    error(s)
    sys exit 1
  }

  def expected(s: String): Nothing = abort(s"$s expected")

  def isAlpha(c: Char): Boolean = c.isLetter

  def isDigit(c: Char): Boolean = c.isDigit

  def emit(s: String): Unit = {
    print("    ")
    print(s)
  }

  def emitLn(s: String): Unit = {
    emit(s)
    println()
  }

  def main(args: Array[String]): Unit = {
    val in = new StreamReader(System.in)
    val compiler = new Compiler(in)
    compiler.emitPrologue()
    compiler.expression()
    compiler.emitEpilogue()
  }

}
