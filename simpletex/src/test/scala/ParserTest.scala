package simpletex.lexer;
import org.scalatest.funsuite.AnyFunSuite

import simpletex.lexer._;
import simpletex.parser._;

class BasicParser extends AnyFunSuite {
  test("Parsing a section") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("hi"),
        TEXT("world"),
        NEWLINE(),
        TEXT("fun"),
        TEXT("yayy"),
        SECTION(),
        TEXT("Dog"),
        TEXT("cat"),
        NEWLINE(),
        TEXT("woof"),
        TEXT("woof")
      )
    ) match {
      case Left(e) =>
        fail(s"Didn't parse the left symbol for bold italics at all: $e")
      case Right(v) =>
        assert(true)
    }
  }

  test("Bold should parse correctly") {
    SimpleTexParser(
      Seq(
        BOLDL(),
        TEXT("hello"),
        TEXT("world"),
        TEXT("hi"),
        BOLDR()
      )
    ) match {
      case Left(value)  => fail(s"We didn't parse this correctly: $value")
      case Right(value) => assert(true)
    }
  }

  test("Section with bold content parses") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("hi"),
        TEXT("world"),
        NEWLINE(),
        BOLDL(),
        TEXT("hello"),
        TEXT("world"),
        TEXT("hi"),
        BOLDR()
      )
    ) match {
      case Left(value)  => fail(s"We didn't parse this correctly: $value")
      case Right(value) => assert(true)
    }
  }

  test("Bold italics should parse correctly") {
    SimpleTexParser(
      Seq(
        BOLDITALICSL(),
        TEXT("hello"),
        TEXT("world"),
        BOLDITALICSR()
      )
    ) match {
      case Left(value)  => fail(s"We didn't parse this correctly: $value")
      case Right(value) => assert(true)
    }
  }
  test("italics should parse correctly") {
    SimpleTexParser(
      Seq(
        ITALICSL(),
        TEXT("hello"),
        TEXT("world"),
        ITALICSR()
      )
    ) match {
      case Left(value)  => fail(s"We didn't parse this correctly: $value")
      case Right(value) => assert(true)
    }
  }
  test("equation should parse correctly") {
    SimpleTexParser(
      Seq(
        EQUATIONL(),
        TEXT("4x^2"),
        TEXT("+"),
        TEXT("5"),
        EQUATIONR()
      )
    ) match {
      case Left(value)  => fail(s"We didn't parse this correctly: $value")
      case Right(value) => assert(true)
    }
  }
  test("image should parse correctly") {
    SimpleTexParser(
      Seq(
        EXSQUARE(),
        TEXT("Some"),
        TEXT("Description"),
        SQUARER(),
        PARENL(),
        TEXT("./some/path"),
        PARENR()
      )
    ) match {
      case Left(value)  => fail(s"We didn't parse this correctly: $value")
      case Right(value) => assert(true)
    }

  }
  test("citation should parse correctly") {
    SimpleTexParser(
      Seq(
        CITATION(),
        BRACEL(),
        TEXT("Some"),
        TEXT("Citation"),
        BRACER()
      )
    ) match {
      case Left(value)  => fail(s"We didn't parse this correctly: $value")
      case Right(value) => assert(true)
    }
  }
  test("reference should parse correctly") {
    SimpleTexParser(
      Seq(
        REFERENCE(),
        BRACEL(),
        TEXT("Some"),
        TEXT("Reference"),
        BRACER()
      )
    ) match {
      case Left(value)  => fail(s"We didn't parse this correctly: $value")
      case Right(value) => assert(true)
    }
  }
}
