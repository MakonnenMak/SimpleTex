package simpletex.lexer;
import org.scalatest.funsuite.AnyFunSuite

import simpletex.lexer._;

class BoldItalics extends AnyFunSuite {
  test("should parse left symbol for bold italics") {
    SimpleTexLexer("""/*** """) match {
      case Left(value) =>
        fail("Didn't parse the left symbol for bold italics at all")
      case Right(List(BOLDITALICSL())) => assert(true)
      case Right(_) =>
        fail("Parsed but returned a different parser result")
    }
  }
  test("should parse right symbol for bold italics") {
    SimpleTexLexer(""" ***/""") match {
      case Left(value) =>
        fail(s"${value} Didn't parse the right symbol for bold italics at all")
      case Right(List(BOLDITALICSR())) => assert(true)
      case Right(_) =>
        fail("Parsed but returned a different parser result")
    }
  }
}

class Italics extends AnyFunSuite {
  test("should parse left symbol for italics") {
    SimpleTexLexer("""/* """) match {
      case Left(value) =>
        fail("Didn't parse the left symbol for italics at all")
      case Right(List(ITALICSL())) => assert(true)
      case Right(_) =>
        fail("Parsed but returned a different parser result")

    }
  }
  test("should parse right symbol for italics") {
    SimpleTexLexer(""" */""") match {
      case Left(value) =>
        fail(s"${value} Didn't parse the right symbol for italics at all")
      case Right(List(ITALICSR())) => assert(true)
      case Right(_) =>
        fail("Parsed but returned a different parser result")

    }
  }
}
class Bold extends AnyFunSuite {
  test("should parse left symbol for bold") {
    SimpleTexLexer("""/** """) match {
      case Left(value) =>
        fail(s"${value} Didn't parse the left symbol for bold at all")
      case Right(List(BOLDL())) => assert(true)
      case Right(_) =>
        fail("Parsed but returned a different parser result")

    }
  }
  test("should parse right symbol for bold") {
    SimpleTexLexer(" **/") match {
      case Left(value) =>
        fail(s"$value Didn't parse the right symbol for bold at all")
      case Right(List(BOLDR())) => assert(true)
      case Right(_) =>
        fail("Parsed but returned a different parser result")
    }
  }
}

class SectionLexer extends AnyFunSuite {
  test("should parse a section on a single line") {
    SimpleTexLexer("# ") match {
      case Left(value) =>
        assert(false, "Didn't parse the section out of the string")
      case Right(List(SECTION())) => assert(true)
      case Right(_)               => assert(false, "We returned more than one section")
    }
  }
  test("should parse a sub-section on a single line") {
    SimpleTexLexer("## ") match {
      case Left(value) =>
        assert(false, "Didn't parse the subsection out of the string")
      case Right(List(SUBSECTION())) => assert(true)
      case Right(_)                  => assert(false, "We returned more than one subsection")
    }
  }

}

class ReferenceLexer extends AnyFunSuite {
  test("should parse an individual reference on its own line") {
    SimpleTexLexer("@ref") match {
      case Left(value) =>
        fail("Didn't parse the reference out of the string")
      case Right(List(REFERENCE())) => assert(true)
      case Right(_)                 => fail("We returned more than one references")
    }
  }

}

class CitationLexer extends AnyFunSuite {
  test("should parse an individual citation on it's own line") {
    SimpleTexLexer("@cite") match {
      case Left(value) =>
        assert(false, "Didn't parse the citation out of the string")
      case Right(List(CITATION())) => assert(true)
      case Right(_)                => assert(false, "We returned more than one citations")
    }
  }
}
class EquationLexer extends AnyFunSuite {

  test("should parse left side tokens") {
    SimpleTexLexer("""/$""") match {
      case Left(e) =>
        fail(s"Didn't parse the equation out of the string: $e")
      case Right(List(EQUATIONL())) => assert(true)
      case Right(_)                 => assert(false, "We returned more than one equation")
    }
  }

  test("should parse right equation token") {
    SimpleTexLexer("""$/""") match {
      case Left(e) =>
        fail(s"Didn't parse the equation out of the string: $e")
      case Right(List(EQUATIONR())) => assert(true)
      case Right(_)                 => assert(false, "We returned more than one equation")
    }

  }
}

/*
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
  test("should not parse text that has only one *** into bold and italics") {
    SimpleTexLexer("***some content") match {
      case Left(value) => fail("Didn't parse the string at all")
      case Right(List(BOLDITALICS("some content"))) =>
        fail("We actually parsed this into a bolditalics when we shouldn't")
      case Right(_) =>
        assert(true)
    }
  }
  test(
    "should parse text surrounded by *** as bold and italics irregardless of spacing"
  ) {
    SimpleTexLexer("*** some content ***") match {
      case Left(value)                                => fail("Didn't parse the string at all")
      case Right(List(BOLDITALICS(" some content "))) => assert(true)
      case Right(_) =>
        fail("Parsed but returned a different parser result")
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


class LayoutLexer extends AnyFunSuite {
  test("should parse a layout on a line correctly") {
    SimpleTexLexer("%% layout1 \n") match {
      case Left(value)                       => assert(false, "Didn't parse layout out of string")
      case Right(List(LAYOUT("layout1 \n"))) => assert(true)
      case Right(_)                          => assert(false, "We returned more than one layout")
    }
  }
}

class LabelLexer extends AnyFunSuite {
  test("should parse any content in a @label tag") {
    SimpleTexLexer("@label{some reference}\n") match {
      case Left(value)                          => assert(false, "Didn't parse label out of string")
      case Right(List(LABEL("some reference"))) => assert(true)
      case Right(_)                             => assert(false, "We returned more than one layout")
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
 */
