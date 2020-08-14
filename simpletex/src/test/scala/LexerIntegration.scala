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

class LexerIntegrationTextFormatTests extends AnyFunSuite {}
