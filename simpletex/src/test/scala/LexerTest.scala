package simpletex.lexer;
import org.scalatest.funsuite.AnyFunSuite

import simpletex.lexer._;

class LexerSectionTests extends AnyFunSuite {
  test("A section on a single line should produce a SECTION object") {
    SimpleTexLexer("# section name \n") match {
      case Left(value) =>
        assert(false, "Didn't parse the section out of the string")
      case Right(value :: Nil) =>
        assert(value.equals(SECTION("section name \n")))
      case Right(_) => assert(false, "We returned more than one section")
    }
  }
}
