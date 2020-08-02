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
  test("A sub-section on a single line should produce a SUBSECTION object") {
    SimpleTexLexer("## subsection name \n") match {
      case Left(value) =>
        assert(false, "Didn't parse the subsection out of the string")
      case Right(value :: Nil) =>
        assert(value.equals(SUBSECTION("subsection name \n")))
      case Right(_) => assert(false, "We returned more than one subsection")
    }
  }
  test(
    "A individual reference on it's own line should produce a REFERENCE object"
  ) {
    SimpleTexLexer("@ref{some reference material} \n") match {
      case Left(value) =>
        assert(false, "Didn't parse the reference out of the string")
      case Right(value :: Nil) =>
        assert(value.equals(REFERENCE("some reference material")))
      case Right(_) => assert(false, "We returned more than one references")
    }
  }
}

class LexerImageTests extends AnyFunSuite {

  test("A simple image should have caption, label, and path") {
    SimpleTexLexer("![My Caption][My Label](/this/is/a/path)") match {
      case Left(value) =>
        assert(false, "Didn't parse the image out of the string")
      case Right(value :: Nil) =>
        assert(value.equals(IMAGE("My Caption", "My Label", "/this/is/a/path")))
      case Right(_) => assert(false, "We returned more than one images")
    }
  }

}
