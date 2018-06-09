package uk.me.mcnulty.lbac

class Compiler(in: Reader) {

  import in.{ look, getNum, matchChar }
  import Cradle.expected

  def expression(): List[String] = {
    var result = term()
    while (Seq('+', '-') contains look) {
      result :+= "push rax"
      look match {
        case '+' => result ++= add()
        case '-' => result ++= subtract()
        case _   => expected("addop")
      }
    }
    result
  }

  def term(): List[String] = {
    var result = factor()
    while (Seq('*', '/') contains look) {
      result :+= "push rax"
      look match {
        case '*' => result ++= multiply()
        case '/' => result ++= divide()
        case _   => expected("mulop")
      }
    }
    result
  }

  def add(): List[String] = {
    matchChar('+')
    term() ++ List(
      "pop rdx",
      "add rax, rdx")
  }

  def subtract(): List[String] = {
    matchChar('-')
    term() ++ List(
      "pop rdx",
      "sub rax, rdx",
      "neg rax")
  }

  def multiply(): List[String] = {
    matchChar('*')
    factor() ++ List(
     "pop rdx",
     "mul rdx")
  }

  def divide(): List[String] = {
    matchChar('/')
    factor() ++ List(
      "mov rcx, rax",
      "pop rax",
      "mov rdx, 0",
      "div rcx")
  }

  def factor(): List[String] = {
    if (look == '(') {
      matchChar('(')
      val result = expression()
      matchChar(')')
      result
    } else {
      List(s"mov rax, ${getNum()}")
    }
  }

}
