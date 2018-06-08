package uk.me.mcnulty

object Cradle {

  val TAB = '\t'

  var look: Char = _

  def getChar(): Unit = {
    val read = System.in.read()
    require(read >= 0, "EOF")
    look = read.toChar
  }

  def error(s: String): Unit = {
    println();
    println(s"Error: $s.")
  }

  def abort(s: String): Nothing = {
    error(s)
    sys exit 1
  }

  def expected(s: String): Nothing = {
    abort(s"$s expected")
  }

  def matchChar(x: Char): Unit = {
    if (look == x) getChar()
    else expected(s"'$x'")
  }

  def isAlpha(c: Char): Boolean = {
    c.toUpper >= 'A' && c.toUpper <= 'Z'
  }

  def isDigit(c: Char): Boolean = {
    c.isDigit
  }

  def getName(): Char = {
    if (! isAlpha(look)) expected("Name")
    else {
      val result = look.toUpper
      getChar()
      result
    }
  }

  def getNum(): Char = {
    if (! isDigit(look)) expected("Integer")
    else {
      val result = look
      getChar()
      result
    }
  }

  def emit(s: String): Unit = {
    print(TAB)
    print(s)
  }

  def emitLn(s: String): Unit = {
    emit(s)
    println()
  }

  def init(): Unit = {
    getChar()
  }

  def main(args: Array[String]): Unit = {
    init()
  }

}
