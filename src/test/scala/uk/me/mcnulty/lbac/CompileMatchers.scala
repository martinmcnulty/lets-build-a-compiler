package uk.me.mcnulty.lbac

import org.scalatest.matchers.{ MatchResult, Matcher }

trait CompileMatchers {

  class ProducesAssemblyCodeMatcher(expected: List[String]) extends Matcher[CompileResult] {
    def apply(actual: CompileResult): MatchResult = {
      val failureMessage = actual match {
        case SuccessfulCompile(code) => s"Expected generated code:\n${expected mkString "\n"}\n\nbut got:\n${code mkString "\n"}"
        case AbortedCompile(msg)     => s"Expected successful compile, but aborted with [$msg]"
      }
      MatchResult(
        actual == SuccessfulCompile(expected),
        failureMessage,
        "Generated code matched expectation")
    }
  }

  class AbortsWithMessageMatcher(expected: String) extends Matcher[CompileResult] {
    def apply(actual: CompileResult): MatchResult = {
      val failureMessage = actual match {
        case SuccessfulCompile(code) => s"Expected compiler to abort with [$expected], but instead it generated:\n${code mkString "\n"}"
        case AbortedCompile(msg)     => s"Expected compiler to abort with [$expected], but actual message was [$msg]"
      }
      MatchResult(
        actual == AbortedCompile(expected),
        failureMessage,
        s"Compiler failed as expected with [$expected]")
    }
  }

  def produce(expected: List[String]) = new ProducesAssemblyCodeMatcher(expected)

  def abortWith(msg: String) = new AbortsWithMessageMatcher(msg)

}

object CompileMatchers extends CompileMatchers
