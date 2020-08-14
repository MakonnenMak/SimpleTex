package simpletex.lexer
import org.scalatest.funsuite.AnyFunSuite
import simpletex.lexer._

// TODO use the word integration in the test names
class LexerIntegrationHeaderTests extends AnyFunSuite {
  test("A section with a subsection directly below it") {
    SimpleTexLexer("# section name \n ## subsection name \n") match {
      case Left(value) =>
        assert(
          false,
          "Didn't parse the section and subsection out of the string"
        )
      case Right(
            List(SECTION("section name \n"), SUBSECTION("subsection name \n"))
          ) =>
        assert(true)
      case Right(_) =>
        assert(
          false,
          "We returned more than a section and subsection"
        ) // TO DO, there are other possibilities here. Should add more cases
    }
  }
}

// TODO Some short naming convention for this..
class LexerIntegrationSectionTextFormatTests extends AnyFunSuite {
  test("A section with a bold piece of text below it") {
    SimpleTexLexer("# section name \n **Hello World!**") match {
      case Left(value) =>
        assert(
          false,
          "Didn't parse the section and bold text out of the string"
        )
      case Right(
            List(SECTION("section name \n"), BOLD("Hello World!"))
          ) =>
        assert(true)
      case Right(_) =>
        assert(
          false,
          "We returned more than a section and a bold piece of text"
        )
    }
  }

  test("A section with a italisized piece of text below it") {
    SimpleTexLexer("# section name \n *Fancy italic text*") match {
      case Left(value) =>
        assert(
          false,
          "Didn't parse the section and italisized text out of the string"
        )
      case Right(
            List(SECTION("section name \n"), ITALICS("Fancy italic text"))
          ) =>
        assert(true)
      case Right(_) =>
        assert(
          false,
          "We returned more than a section and an italisized piece of text"
        )
    }
  }

  test("A section with a bold+italisized piece of text below it") {
    SimpleTexLexer("# section name \n ***Bold and fancy italics***") match {
      case Left(value) =>
        assert(
          false,
          "Didn't parse the section and bold+italisized text out of the string"
        )
      case Right(
            List(
              SECTION("section name \n"),
              BOLDITALICS("Bold and fancy italics")
            )
          ) =>
        assert(true)
      case Right(_) =>
        assert(
          false,
          "We returned more than a section and a bold+italisized piece of text"
        )
    }
  }

  test("A section with a reference below it") {
    SimpleTexLexer("# section name \n @ref{some label} ") match {
      case Left(value) =>
        assert(
          false,
          "Didn't parse the section and reference out of the string"
        )
      case Right(
            List(SECTION("section name \n"), REFERENCE("some label"))
          ) =>
        assert(true)
      case Right(_) =>
        assert(
          false,
          "We returned more than a section and a reference"
        )
    }
  }

  test("A section with a citation below it") {
    SimpleTexLexer(
      "# section name \n @cite{some citation} "
    ) match {
      case Left(value) =>
        assert(
          false,
          "Didn't parse the section and citation out of the string"
        )
      case Right(
            List(SECTION("section name \n"), CITATION("some citation"))
          ) =>
        assert(true)
      case Right(_) =>
        assert(
          false,
          "We returned more than a section and a citation"
        )
    }
  }
}
