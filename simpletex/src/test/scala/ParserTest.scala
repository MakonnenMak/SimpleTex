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
}
