package simpletex.lexer;
import org.scalatest.funsuite.AnyFunSuite

import simpletex.lexer._;

class LexerSectionTests extends AnyFunSuite {
  test("A section on a single line should produce a SECTION object") {
    SimpleTexLexer("# section name \n") match {
      case Left(value) =>
        assert(false, "Didn't parse the section out of the string")
      case Right(List(SECTION("section name \n"))) => assert(true)
      case Right(_)                                => assert(false, "We returned more than one section")
    }
  }
  test("A sub-section on a single line should produce a SUBSECTION object") {
    SimpleTexLexer("## subsection name \n") match {
      case Left(value) =>
        assert(false, "Didn't parse the subsection out of the string")
      case Right(List(SUBSECTION("subsection name \n"))) => assert(true)
      case Right(_)                                      => assert(false, "We returned more than one subsection")
    }
  }
  test(
    "A individual reference on it's own line should produce a REFERENCE object"
  ) {
    SimpleTexLexer("@ref{some reference material} \n") match {
      case Left(value) =>
        assert(false, "Didn't parse the reference out of the string")
      case Right(List(REFERENCE("some reference material"))) => assert(true)
      case Right(_)                                          => assert(false, "We returned more than one references")
    }
  }
}

class LexerBoldTest extends AnyFunSuite {
  test("A text surrounded by two * should be bold") {
    SimpleTexLexer("**some bold text**") match {
      case Left(value)                         => fail("Didn't parse the bold text")
      case Right(List(BOLD("some bold text"))) => assert(true)
      case Right(a)                            => fail(s"Parsed but not the right object: $a")
    }
  }

  test("A text with only one pair of * shouldn't parse as a bold") {
    SimpleTexLexer("**some bold text") match {
      case Left(value) => fail("Lexer couldn't understand this")
      case Right(List(BOLD("some bold te"))) =>
        fail("We parsed this into a bold incorrectly")
      case Right(List(CONTENT("**some bold text"))) => assert(true)
      case Right(_) =>
        fail("We parsed this into something other than a content object")
    }
  }

  test("Empty space surrounded by ** should parse as a bold") {
    SimpleTexLexer("** **") match {
      case Left(value)            => fail("Lexer couldn't understand this")
      case Right(List(BOLD(" "))) => assert(true)
      case Right(a)               => fail(s"We parsed it into something else: $a")
    }
  }

  test("Space doesn't matter as long as we have at least one non-empty char") {
    SimpleTexLexer("** some space surrounded **") match {
      case Left(value)                                  => fail("Lexer couldn't understand this")
      case Right(List(BOLD(" some space surrounded "))) => assert(true)
      case Right(a)                                     => fail(s"Prased into something else: $a")
    }
  }

}

class LexerItalicsTests extends AnyFunSuite {
  test("Text surrounded by one * should be italics") {
    SimpleTexLexer("*some text*") match {
      case Left(value)                       => fail("Lexer couldn't understand this")
      case Right(List(ITALICS("some text"))) => assert(true)
      case Right(a)                          => fail(s"Prased into something else: $a")
    }
  }

  test("Text surrounded with a single * shouldn't be italics") {
    SimpleTexLexer("*some text here") match {
      case Left(value) => fail("Lexer couldn't understand this")
      case Right(List(ITALICS(" "))) =>
        fail("We incorrectly parsed this into a italics")
      case Right(_) => assert(true)
    }
  }

  test(
    "Empty space doesn't matter as long as we have at least one non-empty space"
  ) {
    SimpleTexLexer("* some text here *") match {
      case Left(value)                              => fail("Lexer couldn't understand this")
      case Right(List(ITALICS(" some text here "))) => assert(true)
      case Right(a)                                 => fail(s"Prased into something else: $a")
    }
  }

  test("Empty space surrounded by * should parse as a italics") {
    SimpleTexLexer("* *") match {
      case Left(value)               => fail("Lexer couldn't understand this")
      case Right(List(ITALICS(" "))) => assert(true)
      case Right(a)                  => fail(s"We parsed it into something else: $a")
    }
  }

}

class LexerImageTests extends AnyFunSuite {

  test("A simple image should have caption, label, and path") {
    SimpleTexLexer("![My Caption][My Label](/this/is/a/path)") match {
      case Left(value) =>
        assert(false, "Didn't parse the image out of the string")
      case Right(List(value)) =>
        assert(value.equals(IMAGE("My Caption", "My Label", "/this/is/a/path")))
      case Right(_) => assert(false, "We returned more than one images")
    }
  }
}

class LexerEquationTests extends AnyFunSuite {

  test("A simple equation is content surrounded by $ signs") {
    SimpleTexLexer("$1+1=2$") match {
      case Left(value) =>
        assert(false, "Didn't parse the equation out of the string")
      case Right(List(value)) => assert(value.equals(EQUATION("1+1=2")))
      case Right(_)           => assert(false, "We returned more than one equation")
    }
  }
}

class LexerLayoutTest extends AnyFunSuite {
  test("A simple layout definition") {
    SimpleTexLexer("%% layout1 \n") match {
      case Left(value) =>
        assert(false, "Didn't parse layout out of string")
      case Right(List(value)) => assert(value.equals(LAYOUT("layout1 \n")))
      case Right(_)           => assert(false, "We returned more than one layout")
    }
  }
}

class LexerContentTest extends AnyFunSuite {

  test("Anything should match the content if not matched by others") {
    SimpleTexLexer("hello world") match {
      case Left(value) =>
        assert(false, "Didn't parse the content out of the string")
      case Right(List(CONTENT("hello world"))) => assert(true)
      case Right(_)                            => assert(false, "We returned more than one content")
    }
  }

  test("An item shouldn't match content if a section can match it") {
    SimpleTexLexer("# hello world \n") match {
      case Left(value) =>
        assert(false, "Didn't parse the content out of the string")
      case Right(List(SECTION("hello world \n"))) => assert(true)
      case Right(a)                               => assert(false, s"We returned more than one content $a")
    }
  }

  test("A section followed by some content should form a list") {
    SimpleTexLexer("# hello section \n some content here") match {
      case Left(value) =>
        assert(false, "Didn't parse the content and section out of the string")
      case Right(
            List(SECTION("hello section \n"), CONTENT("some content here"))
          ) =>
        assert(true)
      case Right(a) =>
        assert(
          false,
          s"We returned incorrect types or more items than expected $a"
        )
    }
  }
}
