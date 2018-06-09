package uk.me.mcnulty.lbac

import java.io.InputStream

trait Reader {

  import Cradle.{ isAlpha, isDigit, expected }

  def look: Char

  def getChar(): Unit

  def matchChar(x: Char): Unit = {
    if (look == x) getChar()
    else expected(s"'$x'")
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

}

class StreamReader(in: InputStream) extends Reader {
  private var nextChar: Int = -1
  def look: Char = if (nextChar < 0) Cradle.abort("eof") else nextChar.toChar
  def getChar(): Unit = nextChar = System.in.read()
  getChar()
}
