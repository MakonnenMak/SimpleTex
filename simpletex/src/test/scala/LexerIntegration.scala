package simpletex.lexer
import org.scalatest.funsuite.AnyFunSuite
import simpletex.lexer._

// TODO use the word I in the test names
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
