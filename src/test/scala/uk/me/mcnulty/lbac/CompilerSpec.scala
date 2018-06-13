package uk.me.mcnulty.lbac

import org.scalatest._
import FakeErrorHandling.FakeAbortException
import CompileMatchers._

class CompilerSpec extends FlatSpec with Matchers {

  "The compiler" should "compile single digit additions" in {
    compileExpression("3+2") should produce(List(
      "    mov rax, 3",
      "    push rax",
      "    mov rax, 2",
      "    pop rdx",
      "    add rax, rdx"))
  }

  it should "fail if there is no digit after the +" in {
    compileExpression("3+") should abortWith("Integer expected")
  }

  it should "handle unary minus" in {
    compileExpression("-1") should produce(List(
      "    mov rax, 0",
      "    push rax",
      "    mov rax, 1",
      "    pop rdx",
      "    sub rax, rdx",
      "    neg rax"))
  }

  it should "load variables in expressions" in {
    compileExpression("a+3") should produce(List(
      "    mov rax, A",
      "    push rax",
      "    mov rax, 3",
      "    pop rdx",
      "    add rax, rdx"))
  }

  it should "handle call sites for functions with no parameters" in {
    compileExpression("f()") should produce(List(
      "    call F"))
  }

  it should "reject two expressions separated by a space" in {
    compile("1+2 3+4") should abortWith("Newline expected")
  }

  def compileExpression(input: String): CompileResult = wrap(input, _.expression())

  def compile(input: String): CompileResult = wrap(input, _.compile())

  private def wrap(input: String, f: Compiler => Unit): CompileResult = {
    val err = new FakeErrorHandling
    val in = new StringReader(err, input + '\u0000')
    val out = new CapturingWriter
    val compiler = new Compiler(err, in, out)
    try {
      f(compiler)
      SuccessfulCompile(out.captured)
    } catch {
      case e: FakeAbortException => AbortedCompile(e.msg)
    }
  }

}

sealed trait CompileResult
case class SuccessfulCompile(assembly: List[String]) extends CompileResult
case class AbortedCompile(msg: String) extends CompileResult

object FakeErrorHandling {

  case class FakeAbortException(msg: String) extends RuntimeException

}

class FakeErrorHandling extends ErrorHandling {

  import FakeErrorHandling._

  def abort(s: String): Nothing = throw new FakeAbortException(s)
  def error(s: String): Unit = ()

}

class StringReader(err: ErrorHandling, in: String) extends Reader {

  var ix = 0

  def getChar(): Unit = ix += 1
  def look: Char = if (ix < in.length) in.charAt(ix) else err.abort("eof")
  protected override def expected(s: String) = err.expected(s)

}

class CapturingWriter extends Writer {

  private var written = ""

  override protected def write(s: String): Unit = written += s

  def captured: List[String] = written.split("\n").toList

}
