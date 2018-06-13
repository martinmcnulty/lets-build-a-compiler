package uk.me.mcnulty.lbac

object Cradle {

  def main(args: Array[String]): Unit = {
    val err = new DefaultErrorHandling
    val in = new StreamReader(err, System.in)
    val out = new StreamWriter(System.out)
    val compiler = new Compiler(err, in, out)
    compiler.compile()
  }

}
