package uk.me.mcnulty.lbac

object Cradle {

  def main(args: Array[String]): Unit = {
    val err = new DefaultErrorHandling
    val in = new StreamReader(err, System.in)
    val out = new StreamWriter(System.out)
    val compiler = new Compiler(err, in, out)
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
