package uk.me.mcnulty.lbac

import java.io.OutputStream

trait Writer {

  def emit(s: String): Unit = {
    write("    ")
    write(s)
  }

  def emitLn(s: String): Unit = {
    emit(s)
    write("\n")
  }

  def emitBlock(s: String): Unit = {
    write(s)
    write("\n")
  }

  protected def write(s: String): Unit

}

class StreamWriter(out: OutputStream) extends Writer {

  val w = new java.io.OutputStreamWriter(out)

  override protected def write(s: String): Unit = {
    w.write(s)
    w.flush()
  }

}
