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
  test(
    "A individual citation on it's own line should produce a CITATION object"
  ) {
    SimpleTexLexer("@cite{some citation material} \n") match {
      case Left(value) =>
        assert(false, "Didn't parse the citation out of the string")
      case Right(List(CITATION("some citation material"))) => assert(true)
      case Right(_)                                        => assert(false, "We returned more than one citations")
    }
  }
}

class BoldLexer extends AnyFunSuite {
  test("should parse all text surrounded by a pair of ** as bold") {
    SimpleTexLexer("**some bold text**") match {
      case Left(value)                         => fail("Didn't parse the bold text")
      case Right(List(BOLD("some bold text"))) => assert(true)
      case Right(a)                            => fail(s"Parsed but not the right object: $a")
    }
  }

  test("should not parse text surrounded by a single ** as bold ") {
    SimpleTexLexer("**some bold text") match {
      case Left(value) => fail("Lexer couldn't understand this")
      case Right(List(BOLD("some bold te"))) =>
        fail("We parsed this into a bold incorrectly")
      case Right(List(CONTENT("**some bold text"))) => assert(true)
      case Right(_) =>
        fail("We parsed this into something other than a content object")
    }
  }

  test("should parse empty space surrounded by ** as bold") {
    SimpleTexLexer("** **") match {
      case Left(value)            => fail("Lexer couldn't understand this")
      case Right(List(BOLD(" "))) => assert(true)
      case Right(a)               => fail(s"We parsed it into something else: $a")
    }
  }

  test(
    "should correctly parse any content surrounded by ** irregardless of spacing"
  ) {
    SimpleTexLexer("** some space surrounded **") match {
      case Left(value)                                  => fail("Lexer couldn't understand this")
      case Right(List(BOLD(" some space surrounded "))) => assert(true)
      case Right(a)                                     => fail(s"Prased into something else: $a")
    }
  }

}

class ItalicsLexer extends AnyFunSuite {
  test("should parse all text surrounded by a pair of * as bold") {
    SimpleTexLexer("*some text*") match {
      case Left(value)                       => fail("Lexer couldn't understand this")
      case Right(List(ITALICS("some text"))) => assert(true)
      case Right(a)                          => fail(s"Prased into something else: $a")
    }
  }

  test("should not parse text surrounded by a single * as italics ") {
    SimpleTexLexer("*some text here") match {
      case Left(value) => fail("Lexer couldn't understand this")
      case Right(List(ITALICS(" "))) =>
        fail("We incorrectly parsed this into a italics")
      case Right(_) => assert(true)
    }
  }

  test("should parse empty space surrounded by * as italics") {
    SimpleTexLexer("* some text here *") match {
      case Left(value)                              => fail("Lexer couldn't understand this")
      case Right(List(ITALICS(" some text here "))) => assert(true)
      case Right(a)                                 => fail(s"Prased into something else: $a")
    }
  }

  test(
    "should correctly parse any content surrounded by * as italics irregardless of spacing"
  ) {
    SimpleTexLexer("* *") match {
      case Left(value)               => fail("Lexer couldn't understand this")
      case Right(List(ITALICS(" "))) => assert(true)
      case Right(a)                  => fail(s"We parsed it into something else: $a")
    }
  }

}

class ImageLexer extends AnyFunSuite {

  test("should correctly parse a simple image with a caption, label, and path") {
    SimpleTexLexer("![My Caption][My Label](/this/is/a/path)") match {
      case Left(value) =>
        assert(false, "Didn't parse the image out of the string")
      case Right(List(value)) =>
        assert(value.equals(IMAGE("My Caption", "My Label", "/this/is/a/path")))
      case Right(_) => assert(false, "We returned more than one images")
    }
  }
}

class EquationLexer extends AnyFunSuite {

  test("should parse a simple equation with content surrounded by $ signs") {
    SimpleTexLexer("$1+1=2$") match {
      case Left(value) =>
        assert(false, "Didn't parse the equation out of the string")
      case Right(List(value)) => assert(value.equals(EQUATION("1+1=2")))
      case Right(_)           => assert(false, "We returned more than one equation")
    }
  }
}

class LayoutLexer extends AnyFunSuite {
  test("should parse a layout on a line correctly") {
    SimpleTexLexer("%% layout1 \n") match {
      case Left(value) =>
        assert(false, "Didn't parse layout out of string")
      case Right(List(value)) => assert(value.equals(LAYOUT("layout1 \n")))
      case Right(_)           => assert(false, "We returned more than one layout")
    }
  }
}

class ContentLexer extends AnyFunSuite {

  test("should match any content if not matched by other parsers") {
    SimpleTexLexer("hello world") match {
      case Left(value) =>
        assert(false, "Didn't parse the content out of the string")
      case Right(List(CONTENT("hello world"))) => assert(true)
      case Right(_)                            => assert(false, "We returned more than one content")
    }
  }

  test("should not match a character if a section can match it") {
    SimpleTexLexer("# hello world \n") match {
      case Left(value) =>
        assert(false, "Didn't parse the content out of the string")
      case Right(List(SECTION("hello world \n"))) => assert(true)
      case Right(a)                               => assert(false, s"We returned more than one content $a")
    }
  }

  test(
    "should match content following a section that is not matched by anything else"
  ) {
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
