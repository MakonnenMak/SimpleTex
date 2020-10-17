package simpletex.parser;
import org.scalatest.funsuite.AnyFunSuite

import simpletex.lexer._;
import simpletex.parser._;

class BasicParser extends AnyFunSuite {

  test("Bold should parse correctly") {
    SimpleTexParser(
      Seq(
        BOLDL(),
        TEXT("hello"),
        TEXT("world"),
        BOLDR()
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(PlainBody(List(Bold(PlainText(List("hello", "world"))))))
          )
          ) =>
        assert(true)
      case Right(value) => fail(s"We parsed something else: $value")

    }
  }

  test("sections should NOT parse correctly") {
    SimpleTexParser(Seq(SECTION(), TEXT("section"), TEXT("title"), NEWLINE())) match {
      case Left(value) => fail(s"We didn't parse it correclty: $value")
      case Right(
          Document(
            List(Section(PlainText(List("section", "title")), List(), List()))
          )
          ) =>
        assert(true)
      case Right(value) => fail(s"We parsed into something incorrect $value")
    }
  }

  test("plaintext should parse correctly") {

    SimpleTexParser(
      Seq(
        TEXT("hello"),
        TEXT("WORLD"),
        TEXT("hello"),
        TEXT("WORLD"),
        TEXT("hello"),
        TEXT("WORLD")
      )
    ) match {
      case Left(value) => fail(s"We didn't parse it correclty: $value")
      case Right(
          Document(
            List(
              PlainBody(
                List(
                  PlainText(
                    List("hello", "WORLD", "hello", "WORLD", "hello", "WORLD")
                  )
                )
              )
            )
          )
          ) =>
        assert(true)
      case Right(value) => fail(s"We parsed into something incorrect $value")
    }
  }

  test("subsections without section's shouldn't parse") {

    SimpleTexParser(
      Seq(SUBSECTION(), TEXT("subsection"), TEXT("title"), NEWLINE())
    ) match {
      case Left(value) => assert(true)
      case Right(_)    => fail("We don't like subsections without sections")
    }

  }

  test("layout section should parse") {}

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
