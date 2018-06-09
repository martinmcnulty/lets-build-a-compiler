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


  def main(args: Array[String]): Unit = {
    val in = new StreamReader(System.in)
    val out = new StreamWriter(System.out)
    val compiler = new Compiler(in, out)
    emitPrologue(out)
    compiler.expression()
    emitEpilogue(out)
  }

  def emitPrologue(out: Writer): Unit = {
    out.emitBlock(
      """|section .text
         |    global _start
         |
         |_start:""".stripMargin)
  }

  def emitEpilogue(out: Writer): Unit = {
    out.emitBlock(
      """|mov rax, 60
         |mov rdi, 0
         |syscall""".stripMargin.indented)
  }

  implicit class StringX(block: String) {
    def indented: String = block split "\n" map ("    " + _) mkString "\n"
  }


}
