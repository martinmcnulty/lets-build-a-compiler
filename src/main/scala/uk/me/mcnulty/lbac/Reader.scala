package uk.me.mcnulty.lbac

import java.io.InputStream

trait Reader {

  def look: Char

  def getChar(): Unit

  protected def expected(s: String): Nothing

  def matchChar(x: Char): Unit = {
    if (look == x) getChar()
    else expected(s"'$x'")
  }

  def getName(): Char = {
    if (!look.isLetter) expected("Name")
    else {
      val result = look.toUpper
      getChar()
      result
    }
  }

  def getNum(): Char = {
    if (!look.isDigit) expected("Integer")
    else {
      val result = look
      getChar()
      result
    }
  }

}

class StreamReader(err: ErrorHandling, in: InputStream) extends Reader {
  private var nextChar: Int = -1
  def look: Char = if (nextChar < 0) err.abort("eof") else nextChar.toChar
  def getChar(): Unit = nextChar = System.in.read()
  override protected def expected(s: String) = err.expected(s)
  getChar()
}
