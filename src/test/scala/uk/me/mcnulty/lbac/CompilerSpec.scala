package uk.me.mcnulty.lbac

import org.scalatest._

import uk.me.mcnulty.lbac.Cradle.abort

class CompilerSpec extends FlatSpec with Matchers {

  "The compiler" should "compile single digit additions" in {
    compile("3+2") should equal(List(
      "    mov rax, 3",
      "    push rax",
      "    mov rax, 2",
      "    pop rdx",
      "    add rax, rdx"
    ))
  }

  def compile(input: String): List[String] = {
    val in = new StringReader(input + '\u0000')
    val out = new CapturingWriter
    val compiler = new Compiler(in, out)
    compiler.expression()
    out.captured
  }

  class StringReader(in: String) extends Reader {

    var ix = 0

    def getChar(): Unit = ix += 1
    def look: Char = if (ix < in.length) in.charAt(ix) else abort("eof")

  }

  class CapturingWriter extends Writer {

    private var written = ""

    override protected def write(s: String): Unit = written += s

    def captured: List[String] = written.split("\n").toList

  }

}
