package simpletex.lexer;
import org.scalatest.funsuite.AnyFunSuite

import simpletex.lexer._;
import simpletex.parser._;

class ParserSectionIntegrationLarge extends AnyFunSuite { //idk what to name the class
  test("Section with layout and a lot of other content") {
    SimpleTexParser(
      Seq(
        LAYOUT(),
        TEXT("l1"),
        NEWLINE(),
        SECTION(),
        TEXT("Intro"),
        TEXT("Section"),
        NEWLINE(),
        BOLDITALICSL(),
        TEXT("hello"),
        TEXT("world"),
        BOLDITALICSR(), //FIX: adding a new line under here will break this test
        TEXT("this"),
        TEXT("is"),
        TEXT("an"),
        BOLDL(),
        TEXT("important"),
        BOLDR(),
        ITALICSL(),
        TEXT("paper"),
        ITALICSR(),
        TEXT("equations"),
        EQUATIONL(),
        TEXT("4x^2+5"),
        EQUATIONR(),
        TEXT("citations"),
        CITATION(),
        BRACEL(),
        TEXT("Some"),
        TEXT("Citation"),
        BRACER(),
        TEXT("Some"),
        TEXT("Reference"),
        REFERENCE(),
        BRACEL(),
        TEXT("Some"),
        TEXT("Reference"),
        BRACER()
      )
    ) match {
      case Left(value) =>
        fail(s"We didn't parse this correctly: $value")
      case Right(value) =>
        assert(true)
    }
  }
}
class ParserSubsectionIntegration extends AnyFunSuite {
  test("subsection with plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        SUBSECTION(),
        TEXT("my"),
        TEXT("subsection"),
        NEWLINE(),
        TEXT("hello"),
        TEXT("world")
      )
    ) match {
      case Left(value)  => fail(s"We didn't parse this correctly: $value")
      case Right(value) => assert(true)
    }
  }
  test("subsection with large plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        SUBSECTION(),
        TEXT("my"),
        TEXT("subsection"),
        NEWLINE(),
        TEXT("this"),
        TEXT("is"),
        TEXT("a"),
        TEXT("large"),
        TEXT("amount"),
        TEXT("of"),
        TEXT("plaintext"),
        TEXT("i"),
        TEXT("think")
      )
    ) match {
      case Left(value)  => fail(s"We didn't parse this correctly: $value")
      case Right(value) => assert(true)
    }
  }
  test("subsection with bold text and plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        SUBSECTION(),
        TEXT("my"),
        TEXT("subsection"),
        NEWLINE(),
        TEXT("hello"),
        BOLDL(),
        TEXT("world"),
        BOLDR()
      )
    ) match {
      case Left(value)  => fail(s"We didn't parse this correctly: $value")
      case Right(value) => assert(true)
    }
  }
  test("subsection with italics text and plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        SUBSECTION(),
        TEXT("my"),
        TEXT("subsection"),
        NEWLINE(),
        TEXT("hello"),
        ITALICSL(),
        TEXT("world"),
        ITALICSR()
      )
    ) match {
      case Left(value)  => fail(s"We didn't parse this correctly: $value")
      case Right(value) => assert(true)
    }
  }
  test("subsection with bolditalics text and plaintext") {
    assert(true)
  }
  test("subsection with citations and plaintext") {
    assert(true)
  }
  test("subsection with references and plaintext") {
    assert(true)
  }
  test("subsection with equations and plaintext") {
    assert(true)
  }
}
