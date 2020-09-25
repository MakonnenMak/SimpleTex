package simpletex.lexer;
import org.scalatest.funsuite.AnyFunSuite

import simpletex.lexer._;

class BoldItalics extends AnyFunSuite {
  test("should not care about spacing near the token [right]") {
    SimpleTexLexer("""***/""") match {
      case Left(e) =>
        fail("Didn't parse the left symbol for bold italics at all")
      case Right(List(BOLDITALICSR())) => assert(true)
      case Right(v) =>
        fail(s"Parsed but returned a different parser result: $v")
    }
  }
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
  test("should not care about spacing near the right token") {
    SimpleTexLexer("""*/""") match {
      case Left(e) =>
        fail("Didn't parse the left symbol for bold italics at all")
      case Right(List(ITALICSR())) => assert(true)
      case Right(v) =>
        fail(s"Parsed but returned a different parser result: $v")
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

  test("should not care about spacing near the right token") {
    SimpleTexLexer("""**/""") match {
      case Left(e) =>
        fail("Didn't parse the left symbol for bold italics at all")
      case Right(List(BOLDR())) => assert(true)
      case Right(v) =>
        fail(s"Parsed but returned a different parser result: $v")
    }
  }
}

class SectionSubSectionLexer extends AnyFunSuite {
  test("should parse a section on a single line") {
    SimpleTexLexer("# ") match {
      case Left(value) =>
        fail("Didn't parse the section out of the string")
      case Right(List(SECTION())) => assert(true)
      case Right(_)               => assert(false, "We returned more than one section")
    }
  }
  test("should parse a sub-section on a single line") {
    SimpleTexLexer("## ") match {
      case Left(value) =>
        fail("Didn't parse the subsection out of the string")
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
        fail("Didn't parse the citation out of the string")
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

class LayoutLexer extends AnyFunSuite {
  test("should parse a layout on a line correctly") {
    SimpleTexLexer("%% ") match {
      case Left(value)           => fail("Didn't parse layout out of string")
      case Right(List(LAYOUT())) => assert(true)
      case Right(_)              => assert(false, "We returned more than one layout")
    }
  }
}

class LabelLexer extends AnyFunSuite {
  test("should parse any content in a @label tag") {
    SimpleTexLexer("@label") match {
      case Left(value)          => fail("Didn't parse label out of string")
      case Right(List(LABEL())) => assert(true)
      case Right(_)             => assert(false, "We returned more than one layout")
    }
  }
}

class SimpleTokenLexers extends AnyFunSuite {
  test("newline should capture the new  line character") {
    SimpleTexLexer("\n") match {
      case Left(value)            => fail(s"Didn't parse the new line: $value")
      case Right(List(NEWLINE())) => assert(true)
      case Right(_) =>
        fail("We returned something other than a single new line")
    }

  }
}
class SquareBracketLexer extends AnyFunSuite {
  test("should parse out left square bracket [") {
    SimpleTexLexer("[") match {
      case Left(value)            => fail(s"Didn't parse the square bracket: $value")
      case Right(List(SQUAREL())) => assert(true)
      case Right(_) =>
        fail("We returned something other than a left square bracket")
    }

  }
  test("should parse out right square bracket ]") {
    SimpleTexLexer("]") match {
      case Left(value)            => fail(s"Didn't parse the square bracket: $value")
      case Right(List(SQUARER())) => assert(true)
      case Right(_) =>
        fail("We returned something other than a right square bracket")
    }

  }
  test("should parse out exclamation !bracket ![") {
    SimpleTexLexer("![") match {
      case Left(value) =>
        fail(s"Didn't parse the exclamation square bracket: $value")
      case Right(List(EXSQUARE())) => assert(true)
      case Right(_) =>
        fail("We returned something other than a exclamation square bracket")
    }

  }

  test("should parse left brace") {
    SimpleTexLexer("{") match {
      case Left(value) =>
        fail(s"Didn't parse the left brace: $value")
      case Right(List(BRACEL())) => assert(true)
      case Right(_) =>
        fail("We returned something other than a left brace")
    }
  }
  test("should parse rigt brace") {
    SimpleTexLexer("}") match {
      case Left(value) =>
        fail(s"Didn't parse the right brace")
      case Right(List(BRACER())) => assert(true)
      case Right(_) =>
        fail("We returned something other than a left brace")
    }
  }

  test("should parse left parentheses") {
    SimpleTexLexer("(") match {
      case Left(value) =>
        fail(s"Didn't parse the left paren: $value")
      case Right(List(PARENL())) => assert(true)
      case Right(_) =>
        fail("We returned something other than a left paren")
    }
  }
  test("should parse rigt parentheses") {
    SimpleTexLexer(")") match {
      case Left(value) =>
        fail(s"Didn't parse the right paren")
      case Right(List(PARENR())) => assert(true)
      case Right(_) =>
        fail("We returned something other than a left paren")
    }
  }
}
