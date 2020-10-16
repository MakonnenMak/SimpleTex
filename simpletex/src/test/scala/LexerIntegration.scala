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
  test("Subsection with equation and plaintext content") {
    SimpleTexLexer("## Subsection \n /$ 4x^2 $/") match {
      case Left(value) => assert(false, s"$value")
      case Right(
          List(
            SUBSECTION(),
            TEXT("Subsection"),
            NEWLINE(),
            EQUATIONL(),
            TEXT("4x^2"),
            EQUATIONR()
          )
          ) =>
        assert(true)
      case Right(_) =>
        assert(
          false,
          "We returned more than a subsection with an equation and text"
        )
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

class ISubsectionTags extends AnyFunSuite {
  test("Subsection with citations and plaintext") {
    SimpleTexLexer(
      // Note: Spaces are required with current lexer in citation
      "## Subsection \n Earth is a planet @cite{ something }"
    ) match {
      case Left(value) => assert(false, s"$value")
      case Right(
          List(
            SUBSECTION(),
            TEXT("Subsection"),
            NEWLINE(),
            TEXT("Earth"),
            TEXT("is"),
            TEXT("a"),
            TEXT("planet"),
            CITATION(),
            BRACEL(),
            TEXT("something"),
            BRACER()
          )
          ) =>
        assert(true)
      case Right(value) =>
        assert(
          false,
          s"$value We returned more than a subsection with a citation and plain text"
        )
    }
  }
  test("Subsection with reference and plaintext") {
    SimpleTexLexer(
      // Note: Spaces are required with current lexer in reference as well
      "## Subsection \n Earth is a planet @ref{ something }"
    ) match {
      case Left(value) => assert(false, s"$value")
      case Right(
          List(
            SUBSECTION(),
            TEXT("Subsection"),
            NEWLINE(),
            TEXT("Earth"),
            TEXT("is"),
            TEXT("a"),
            TEXT("planet"),
            REFERENCE(),
            BRACEL(),
            TEXT("something"),
            BRACER()
          )
          ) =>
        assert(true)
      case Right(value) =>
        assert(
          false,
          s"$value We returned more than a subsection with a reference and plain text"
        )
    }
  }
}

class ISectionTextModifers extends AnyFunSuite {
  test("with a plaintext title should parse correctly") {
    SimpleTexLexer("# section name \n") match {
      case Left(value) =>
        fail("Didn't parse the section out of the string")
      case Right(List(SECTION(), TEXT("section"), TEXT("name"), NEWLINE())) =>
        assert(true)
      case Right(_) => assert(false, "We returned more than one section")
    }
  }
  test("with a plaintext content following section should parse") {
    SimpleTexLexer("# section name \n more plaintext content") match {
      case Left(value) =>
        fail("Didn't parse the section out of the string")
      case Right(
          List(
            SECTION(),
            TEXT("section"),
            TEXT("name"),
            NEWLINE(),
            TEXT("more"),
            TEXT("plaintext"),
            TEXT("content")
          )
          ) =>
        assert(true)
      case Right(_) =>
        assert(false, "We returned the wrong structure or a non section")
    }
  }
  test("with a large plaintext content should parse") {
    SimpleTexLexer(
      "# section name \n  more more more more more more more more more more more more more more more more"
    ) match {
      case Left(value) =>
        fail("Didn't parse the section out of the string")
      case Right(
          List(
            SECTION(),
            TEXT("section"),
            TEXT("name"),
            NEWLINE(),
            TEXT("more"),
            TEXT("more"),
            TEXT("more"),
            TEXT("more"),
            TEXT("more"),
            TEXT("more"),
            TEXT("more"),
            TEXT("more"),
            TEXT("more"),
            TEXT("more"),
            TEXT("more"),
            TEXT("more"),
            TEXT("more"),
            TEXT("more"),
            TEXT("more"),
            TEXT("more")
          )
          ) =>
        assert(true)
      case Right(_) =>
        assert(false, "We returned the wrong structure or a non section")
    }
  }

  test("with plaintext and bold should parse") {
    SimpleTexLexer(
      "# section name \n plaintext mixed with /** bold items **/ should parse"
    ) match {
      case Left(value) => fail("Didn't parse the mixed content correctly")
      case Right(
          List(
            SECTION(),
            TEXT("section"),
            TEXT("name"),
            NEWLINE(),
            TEXT("plaintext"),
            TEXT("mixed"),
            TEXT("with"),
            BOLDL(),
            TEXT("bold"),
            TEXT("items"),
            BOLDR(),
            TEXT("should"),
            TEXT("parse")
          )
          ) =>
        assert(true)
      case Right(wrong) => assert(false, s"We returned something else: $wrong")
    }
  }
  test("with plaintext and bolditalics should parse") {
    SimpleTexLexer(
      "# section name \n plaintext mixed with /*** bolditalics items ***/ should parse"
    ) match {
      case Left(value) => fail("Didn't parse the mixed content correctly")
      case Right(
          List(
            SECTION(),
            TEXT("section"),
            TEXT("name"),
            NEWLINE(),
            TEXT("plaintext"),
            TEXT("mixed"),
            TEXT("with"),
            BOLDITALICSL(),
            TEXT("bolditalics"),
            TEXT("items"),
            BOLDITALICSR(),
            TEXT("should"),
            TEXT("parse")
          )
          ) =>
        assert(true)
      case Right(wrong) => assert(false, s"We returned something else: $wrong")
    }
  }
  test("with plaintext and italics should parse") {
    SimpleTexLexer(
      "# section name \n plaintext mixed with /* italics items */ should parse"
    ) match {
      case Left(value) => fail("Didn't parse the mixed content correctly")
      case Right(
          List(
            SECTION(),
            TEXT("section"),
            TEXT("name"),
            NEWLINE(),
            TEXT("plaintext"),
            TEXT("mixed"),
            TEXT("with"),
            ITALICSL(),
            TEXT("italics"),
            TEXT("items"),
            ITALICSR(),
            TEXT("should"),
            TEXT("parse")
          )
          ) =>
        assert(true)
      case Right(wrong) => assert(false, s"We returned something else: $wrong")
    }
  }
}

class ISectionRefs extends AnyFunSuite {
  test("with plaintext and references should parse") {
    SimpleTexLexer(
      "# section name \n plaintext mixed with @ref{ some label } more plaintext"
    ) match {
      case Left(value) => fail("Didn't parse the mixed content correctly")
      case Right(
          List(
            SECTION(),
            TEXT("section"),
            TEXT("name"),
            NEWLINE(),
            TEXT("plaintext"),
            TEXT("mixed"),
            TEXT("with"),
            REFERENCE(),
            BRACEL(),
            TEXT("some"),
            TEXT("label"),
            BRACER(),
            TEXT("more"),
            TEXT("plaintext")
          )
          ) =>
        assert(true)
      case Right(wrong) => assert(false, s"We returned something else: $wrong")
    }
  }

  test("with plaintext and equations should parse") {
    SimpleTexLexer(
      "# section name \n plaintext mixed with /$ 1 + 1 = 2 $/ more plaintext"
    ) match {
      case Left(value) => fail("Didn't parse the mixed content correctly")
      case Right(
          List(
            SECTION(),
            TEXT("section"),
            TEXT("name"),
            NEWLINE(),
            TEXT("plaintext"),
            TEXT("mixed"),
            TEXT("with"),
            EQUATIONL(),
            TEXT("1"),
            TEXT("+"),
            TEXT("1"),
            TEXT("="),
            TEXT("2"),
            EQUATIONR(),
            TEXT("more"),
            TEXT("plaintext")
          )
          ) =>
        assert(true)
      case Right(wrong) => assert(false, s"We returned something else: $wrong")
    }
  }

  test("with plaintext and citations should parse") {
    SimpleTexLexer(
      "# section name \n plaintext mixed with @cite{ citations here } more plaintext"
    ) match {
      case Left(value) => fail("Didn't parse the mixed content correctly")
      case Right(
          List(
            SECTION(),
            TEXT("section"),
            TEXT("name"),
            NEWLINE(),
            TEXT("plaintext"),
            TEXT("mixed"),
            TEXT("with"),
            CITATION(),
            BRACEL(),
            TEXT("citations"),
            TEXT("here"),
            BRACER(),
            TEXT("more"),
            TEXT("plaintext")
          )
          ) =>
        assert(true)
      case Right(wrong) => assert(false, s"We returned something else: $wrong")
    }
  }
}
