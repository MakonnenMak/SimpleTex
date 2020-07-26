import org.scalatest.FunSuite;
import simpletex.lexer._

class LexerSectionTests extends FunSuite {
  test("A section on a single line should produce a SECTION object") {
    assert(
      SimpleTexLexer("# section name \n") match {
        case Left(value)  => false
        case Right(value) => true
      }
    )
  }
}
