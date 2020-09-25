package simpletex.lexer
import org.scalatest.funsuite.AnyFunSuite
import simpletex.lexer._

// TODO use the word I in the test names
class ISection extends AnyFunSuite {
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
}
