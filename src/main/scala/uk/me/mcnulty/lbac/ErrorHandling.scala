package uk.me.mcnulty.lbac

trait ErrorHandling {

  def error(s: String): Unit

  def abort(s: String): Nothing

  def expected(s: String): Nothing = abort(s"$s expected")

}

class DefaultErrorHandling extends ErrorHandling {

  override def error(s: String): Unit = {
    println();
    println(s"Error: $s.")
  }

  override def abort(s: String): Nothing = {
    error(s)
    sys exit 1
  }

}
