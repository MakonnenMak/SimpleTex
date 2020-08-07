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

case class SECTION(title: String) extends SimpleTexToken
case class LAYOUT(layout: String) extends SimpleTexToken
case class SUBSECTION(title: String) extends SimpleTexToken
case class BOLD(text: String) extends SimpleTexToken
case class ITALICS(text: String) extends SimpleTexToken
case class BOLDITALICS(text: String) extends SimpleTexToken
case class CITATION(source: String) extends SimpleTexToken
case class REFERENCE(label: String) extends SimpleTexToken
case class IMAGE(label: String, caption: String, path: String)
    extends SimpleTexToken
case class Text(content: String) extends SimpleTexToken
case class EQUATION(equation: String) extends SimpleTexToken
case class CONTENT(content: String) extends SimpleTexToken

trait SimpleTexCompilationError
case class SimpleTexLexerError(msg: String) extends SimpleTexCompilationError;

case object SimpleTexLexer extends RegexParsers {
  def section: Parser[SECTION] = {
    "^# (.*)\n".r ^^ { title => SECTION(title.slice(2, title.length)) }
  }

  // TODO parse out the ## etc for all
  // above method is ugly but works
  def subsection: Parser[SUBSECTION] = {
    "^## (.*)\n".r ^^ { title => SUBSECTION(title.slice(3, title.length)) }
  }

  def layout: Parser[LAYOUT] = {
    "^%% (.*)\n".r ^^ { layout => LAYOUT(layout.slice(3, layout.length)) }
  }

  def italics: Parser[ITALICS] = {
    "\\*(.+)\\*".r ^^ { text => ITALICS(text.slice(1, text.length - 1)) }
  }

  def bold: Parser[BOLD] = {
    "\\*\\*(.+)\\*\\*".r ^^ { text => BOLD(text.slice(2, text.length - 2)) }
  }

  def boldItalics: Parser[BOLDITALICS] = {
    "\\*\\*\\*(.+)\\*\\*\\*".r ^^ { text =>
      BOLDITALICS(text.slice(3, text.length - 3))
    }
  }

  def citation: Parser[CITATION] = {
    "@cite\\{([^}]+)\\}*".r ^^ { citation => CITATION(citation) }
  }

  def reference: Parser[REFERENCE] = {
    "@ref\\{([^}]+)\\}".r ^^ { label =>
      REFERENCE(label.slice(5, label.length - 1))
    }
  }

  def image: Parser[IMAGE] = {
    val matcher = """!\[(.*)\]\[(.*)\]\((.*)\)""".r
    "!\\[(.+)\\]\\[(.+)\\]\\((.+)\\)".r ^^ { content =>
      content match {
        case matcher(caption, label, path) => IMAGE(caption, label, path)
      }
    }

  }

  def equation: Parser[EQUATION] = {
    "\\$.+\\$".r ^^ { equation =>
      EQUATION(equation.slice(1, equation.length - 1))
    }

  }

  def content: Parser[CONTENT] = {
    ".+".r ^^ { content => CONTENT(content) }

  }

  def tokens: Parser[List[SimpleTexToken]] = {
    phrase(
      rep1(
        boldItalics | bold | italics | citation | reference | section | subsection | image | equation | content
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
