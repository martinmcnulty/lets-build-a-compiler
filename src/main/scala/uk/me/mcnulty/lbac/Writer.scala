package uk.me.mcnulty.lbac

import java.io.OutputStream

trait Writer {

  def emit(s: String): Unit

  def emitLn(s: String): Unit

  def emitBlock(s: String): Unit

}

class StreamWriter(out: OutputStream) extends Writer {

  val w = new java.io.OutputStreamWriter(out)

  def emit(s: String): Unit = {
    w.write("    ")
    w.write(s)
    w.flush()
  }

  def emitLn(s: String): Unit = {
    emit(s)
    w.write('\n')
    w.flush()
  }

  def emitBlock(s: String): Unit = {
    w.write(s)
    w.write('\n')
    w.flush()
  }

}
