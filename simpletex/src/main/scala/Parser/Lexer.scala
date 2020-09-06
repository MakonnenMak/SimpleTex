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
case class EXCLAM() extends SimpleTexToken

trait SimpleTexCompilationError
case class SimpleTexLexerError(msg: String) extends SimpleTexCompilationError;

case object SimpleTexLexer extends RegexParsers {
  def section: Parser[SECTION] = {
    "^# ".r ^^ { _ => SECTION() }
  }

  def newline: Parser[NEWLINE] = {
    raw"\n".r ^^ { _ => NEWLINE() }
  }

  def subsection: Parser[SUBSECTION] = {
    "^## ".r ^^ { _ => SUBSECTION() }
  }

  def layout: Parser[LAYOUT] = {
    "^%% ".r ^^ { _ => LAYOUT() }
  }

  def italics: Parser[ITALICS] = {
    raw"/*(.+)*".r ^^ { text => ITALICS() }
  }

  def bold: Parser[BOLD] = {
    "\\*\\*(.+)\\*\\*".r ^^ { text => BOLD() }
  }

  def boldItalics: Parser[BOLDITALICS] = {
    "\\*\\*\\*(.+)\\*\\*\\*".r ^^ { text => BOLDITALICS() }
  }

  def citation: Parser[CITATION] = {
    "@cite\\{([^}]+)\\}*".r ^^ { citation => CITATION() }
  }

  def reference: Parser[REFERENCE] = {
    "@ref\\{([^}]+)\\}".r ^^ { label => REFERENCE() }
  }

  def image: Parser[IMAGE] = {
    val matcher = """!\[(.*)\]\[(.*)\]\((.*)\)""".r
    "!\\[(.+)\\]\\[(.+)\\]\\((.+)\\)".r ^^ { content =>
      content match {
        case matcher(caption, label, path) => IMAGE()
      }
    }

  }

  def equation: Parser[EQUATION] = {
    "\\$.+\\$".r ^^ { equation => EQUATION() }

  }

  def label: Parser[LABEL] = {
    "@label\\{([^}]+)\\}*".r ^^ { label => LABEL() }
  }

  def content: Parser[TEXT] = {
    "\\S+".r ^^ { content => TEXT(content) }
  }

  def tokens: Parser[List[SimpleTexToken]] = {
    phrase(
      rep1(
        boldItalics | bold | italics | citation | reference | section | subsection | image | equation | layout | label | content
      )
    )
  }
  def apply(code: String): Either[SimpleTexLexerError, List[SimpleTexToken]] = {
    parse(tokens, code) match {
      case NoSuccess(msg, next)  => Left(SimpleTexLexerError(msg))
      case Success(result, next) => Right(result);
    }
  }

}
