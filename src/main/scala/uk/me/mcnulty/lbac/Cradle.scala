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
    print("    ")
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
    emitPrologue()
    expression()
    emitEpilogue()
  }

  def expression(): Unit = {
    term()
    while (Seq('+', '-') contains look) {
    emitLn("push rax")
    look match {
      case '+' => add()
      case '-' => subtract()
      case _   => expected("addop")
    }
  }
  }

  def term(): Unit = {
    factor()
    while (Seq('*', '/') contains look) {
      emitLn("push rax")
      look match {
        case '*' => multiply()
        case '/' => divide()
        case _   => expected("mulop")
      }
    }
  }

  def add(): Unit = {
    matchChar('+')
    term()
    emitLn("pop rdx")
    emitLn("add rax, rdx")
  }

  def subtract(): Unit = {
    matchChar('-')
    term()
    emitLn("pop rdx")
    emitLn("sub rax, rdx")
    emitLn("neg rax")
  }

  def multiply(): Unit = {
    matchChar('*')
    factor()
    emitLn("pop rdx")
    emitLn("mul rdx")
  }

  def divide(): Unit = {
    matchChar('/')
    factor()
    emitLn("mov rcx, rax")
    emitLn("pop rax")
    emitLn("mov rdx, 0")
    emitLn("div rcx")
  }

  def factor(): Unit = {
    emitLn(s"mov rax, ${getNum()}")
  }

  def emitPrologue(): Unit = {
    print(
      """|section .text
         |    global _start
         |
         |_start:
         |""".stripMargin)
  }

  def emitEpilogue(): Unit = {
    emitLn("mov rax, 60")
    emitLn("mov rdi, 0")
    emitLn("syscall")
  }

}
