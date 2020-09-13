/**
  * This file contains the scanner of the parser we are building
  *
  * We start with a scanner that uses regular expressions to recognize the
  * language and tokenize it. The following phase will be the responsbility
  * of the grammar implemented in the parser2 file.
  */
package simpletex.lexer
import scala.util.parsing.combinator._

sealed trait SimpleTexToken

case class SECTION() extends SimpleTexToken
case class LAYOUT() extends SimpleTexToken
case class SUBSECTION() extends SimpleTexToken

case class BOLDL() extends SimpleTexToken
case class BOLDR() extends SimpleTexToken
case class ITALICSL() extends SimpleTexToken
case class ITALICSR() extends SimpleTexToken
case class BOLDITALICSL() extends SimpleTexToken
case class BOLDITALICSR() extends SimpleTexToken

case class CITATION() extends SimpleTexToken
case class REFERENCE() extends SimpleTexToken
case class EQUATIONL() extends SimpleTexToken
case class EQUATIONR() extends SimpleTexToken

case class TEXT(value: String) extends SimpleTexToken
case class LABEL() extends SimpleTexToken

case class NEWLINE() extends SimpleTexToken
case class BRACEL() extends SimpleTexToken
case class BRACER() extends SimpleTexToken
case class PARENL() extends SimpleTexToken
case class PARENR() extends SimpleTexToken
case class SQUAREL() extends SimpleTexToken
case class SQUARER() extends SimpleTexToken
case class EXSQUARE() extends SimpleTexToken

trait SimpleTexCompilationError
case class SimpleTexLexerError(msg: String) extends SimpleTexCompilationError;
case object SimpleTexLexer extends RegexParsers {
  override def skipWhitespace = true
  override val whiteSpace = "[ \t\r\f]+".r
  def section: Parser[SECTION] = "^# ".r ^^ { _ => SECTION() }
  def subsection: Parser[SUBSECTION] = "^## ".r ^^ { _ => SUBSECTION() }
  def layout: Parser[LAYOUT] = "^%% ".r ^^ { _ => LAYOUT() }

  def italicsL: Parser[ITALICSL] = raw"/\* ".r ^^ { _ => ITALICSL() }
  def italicsR: Parser[ITALICSR] = raw"\s*\*/".r ^^ { _ => ITALICSR() }
  def boldL: Parser[BOLDL] = raw"/\*\* ".r ^^ { _ => BOLDL() }
  def boldR: Parser[BOLDR] = raw"\s*\*\*/".r ^^ { _ => BOLDR() }
  def boldItalicsL: Parser[BOLDITALICSL] =
    raw"/\*\*\* ".r ^^ { _ => BOLDITALICSL() }
  def boldItalicsR: Parser[BOLDITALICSR] =
    raw"\s*\*\*\*/".r ^^ { _ =>
      BOLDITALICSR()
    }

  def citation: Parser[CITATION] = raw"@cite".r ^^ { _ => CITATION() }
  def reference: Parser[REFERENCE] = raw"@ref".r ^^ { _ => REFERENCE() }

  def equationL: Parser[EQUATIONL] = """/\$""".r ^^ { _ => EQUATIONL() }
  def equationR: Parser[EQUATIONR] = """\$/""".r ^^ { _ => EQUATIONR() }
  def label: Parser[LABEL] = "@label".r ^^ { _ => LABEL() }
  def content: Parser[TEXT] = "\\S+".r ^^ { content => TEXT(content) }

  def newline: Parser[NEWLINE] = raw"\n".r ^^ { _ => NEWLINE() }

  def braceR: Parser[BRACER] = raw"}".r ^^ { _ => BRACER() }
  def braceL: Parser[BRACEL] = raw"{".r ^^ { _ => BRACEL() }
  def parenL: Parser[PARENL] = raw"(".r ^^ { _ => PARENL() }
  def parenR: Parser[PARENR] = raw")".r ^^ { _ => PARENR() }
  def squareL: Parser[SQUAREL] = raw"[".r ^^ { _ => SQUAREL() }
  def squareR: Parser[SQUARER] = raw"]".r ^^ { _ => SQUARER() }
  def exsquare: Parser[EXSQUARE] = raw"!\[".r ^^ { _ => EXSQUARE() }

  def tokens: Parser[List[SimpleTexToken]] = {
    phrase(
      rep1(
        boldItalicsL | boldItalicsR | italicsL | italicsR | boldL | boldR | section | subsection | reference | citation | equationL | equationR | layout | label | newline | exsquare
      )
    )
    //phrase(
    //rep1(
    //citation | reference | section | subsection | boldL | boldR | italicsL | italicsR | boldItalicsL | boldItalicsR | equationL | equationR | layout | label | content | newline | braceL | braceR | parenL | parenR | squareL | squareR | exclam
    //)
    //)
  }
  def apply(code: String): Either[SimpleTexLexerError, List[SimpleTexToken]] = {
    parse(tokens, code) match {
      case NoSuccess(msg, next)  => Left(SimpleTexLexerError(msg))
      case Success(result, next) => Right(result);
    }
  }

}
