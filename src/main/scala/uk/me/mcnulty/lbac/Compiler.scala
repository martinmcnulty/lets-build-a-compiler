package uk.me.mcnulty.lbac

class Compiler(errorHandling: ErrorHandling, in: Reader, out: Writer) {

  import in.{ look, getName, getNum, matchChar }
  import out.emitLn
  import errorHandling.expected

  def expression(): Unit = {
    if (isAddOp(look)) {
      emitLn("mov rax, 0")
    } else {
      term()
    }
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

  /**
   * Handle an identifier, which might be a variable or a function
   * call (we only support empty parameter lists for now)
   */
  def ident(): Unit = {
    val name = getName()
    if (look == '(') {
      matchChar('(')
      matchChar(')')
      emitLn(s"call $name")
    } else {
      emitLn(s"mov rax, $name")
    }
  }

  def factor(): Unit = {
    if (look == '(') {
      matchChar('(')
      expression()
      matchChar(')')
    } else if (look.isLetter) {
      ident()
    } else {
      emitLn(s"mov rax, ${getNum()}")
    }
  }

  def isAddOp(c: Char): Boolean = Seq('+', '-') contains c

}
