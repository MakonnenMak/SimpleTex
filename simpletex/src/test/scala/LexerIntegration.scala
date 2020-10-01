package simpletex.lexer
import org.scalatest.funsuite.AnyFunSuite
import simpletex.lexer._

class ISubsectionPlain extends AnyFunSuite {
  test("Subsection with short plaintext content") {
    SimpleTexLexer("## Subsection") match {
      case Left(value) => assert(false, s"$value")
      case Right(List(SUBSECTION(), TEXT("Subsection"))) =>
        assert(true)
      case Right(_) =>
        assert(false, "We returned more than a subsection with short text")
    }
  }
  test("Subsection with longer plaintext content") {
    SimpleTexLexer("## Subsection With A Lot More Words") match {
      case Left(value) => assert(false, s"$value")
      case Right(
            List(
              SUBSECTION(),
              TEXT("Subsection"),
              TEXT("With"),
              TEXT("A"),
              TEXT("Lot"),
              TEXT("More"),
              TEXT("Words")
            )
          ) =>
        assert(true)
      case Right(_) =>
        assert(false, "We returned more than a subsection with long text")
    }

  }
}

class ISubsectionTextModifier extends AnyFunSuite {
  test("Subsection with bold text and plain text") {
    SimpleTexLexer("## Subsection \n /** Hello **/ world") match {
      case Left(value) => assert(false, s"$value")
      case Right(
            List(
              SUBSECTION(),
              TEXT("Subsection"),
              NEWLINE(),
              BOLDL(),
              TEXT("Hello"),
              BOLDR(),
              TEXT("world")
            )
          ) =>
        assert(true)
      case Right(value) =>
        assert(
          false,
          s"$value We returned more than a subsection with bold text and plain text"
        )
    }
  }
  test("Subsection with italicized text and plaintext") {
    SimpleTexLexer("## Subsection \n /* Hello */ world") match {
      case Left(value) => assert(false, s"$value")
      case Right(
            List(
              SUBSECTION(),
              TEXT("Subsection"),
              NEWLINE(),
              ITALICSL(),
              TEXT("Hello"),
              ITALICSR(),
              TEXT("world")
            )
          ) =>
        assert(true)
      case Right(value) =>
        assert(
          false,
          s"$value We returned more than a subsection with italics text and plain text"
        )
    }
  }
  test("Subsection with bold-italics text and plaintext") {
    SimpleTexLexer("## Subsection \n /*** Hello ***/ world") match {
      case Left(value) => assert(false, s"$value")
      case Right(
            List(
              SUBSECTION(),
              TEXT("Subsection"),
              NEWLINE(),
              BOLDITALICSL(),
              TEXT("Hello"),
              BOLDITALICSR(),
              TEXT("world")
            )
          ) =>
        assert(true)
      case Right(value) =>
        assert(
          false,
          s"$value We returned more than a subsection with bold-italics text and plain text"
        )
    }
  }
}

/*
// TODO use the word I in the test names
class ILexerHeader extends AnyFunSuite {
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

class ILexerSectionSubContent extends AnyFunSuite {
  test("throwaway") {
    SimpleTexLexer("hi hi **bold stuff** hi2 hi3") match {
      case Left(value) =>
        assert(
          false,
          s"Didn't parse the content and bold text out of the string: $value"
        )
      case Right(
          List(
            CONTENT("hi"),
            CONTENT("hi"),
            BOLD("bold stuff"),
            CONTENT("hi2"),
            CONTENT("hi3")
          )
          ) =>
        assert(true)
      case Right(a) =>
        assert(
          false,
          s"We returned more than a section and a bold piece of text: $a"
        )
    }

  }

}
class ILexerSectionFormatting extends AnyFunSuite {
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
 */
