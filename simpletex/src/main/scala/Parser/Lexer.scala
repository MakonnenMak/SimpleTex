/**
  * This file contains the scanner of the parser we are building
  *
  * We start with a scanner that uses regular expressions to recognize the
  * language and tokenize it. The following phase will be the responsbility
  * of the grammar implemented in the parser2 file.
  */
import scala.util.parsing.combinator._

sealed trait SimpleTexToken

case class SECTION(title: String) extends SimpleTexToken
case class LAYOUT(layout: String) extends SimpleTexToken
case class SECTIONLAYOUT(title: SECTION, layout: LAYOUT) extends SimpleTexToken
case class SUBSECTION(title: String) extends SimpleTexToken
case class BOLD(text: String) extends SimpleTexToken
case class ITALICS(text: String) extends SimpleTexToken
case class BOLDITALICS(text: String) extends SimpleTexToken
case class CITATION(source: String) extends SimpleTexToken
case class REFERENCE(label: String) extends SimpleTexToken

class SimpleTexLexer extends RegexParsers {
  def section: Parser[SECTION] = {
    "^# (.*)$".r ^^ { title => SECTION(title.slice(2, title.length)) }
  }

  // TODO parse out the ## etc for all
  // above method is ugly but works
  def subsection: Parser[SUBSECTION] = {
    "^## (.*)$".r ^^ { title => SUBSECTION(title) }
  }

  def layout: Parser[LAYOUT] = {
    "^%% (.*)\n".r ^^ { layout => LAYOUT(layout) }
  }

  def layoutSection: Parser[SECTIONLAYOUT] = layout ~ section ^^ {
    case layout ~ section => SECTIONLAYOUT(section, layout)
  }

  def italics: Parser[ITALICS] = {
    "\\*(.*)\\*".r ^^ { text => ITALICS(text) }
  }

  def bold: Parser[BOLD] = {
    "\\*\\*(.*)\\*\\*".r ^^ { text => BOLD(text) }
  }

  def boldItalics: Parser[BOLDITALICS] = {
    "\\*\\*\\*(.*)\\*\\*\\*".r ^^ { text => BOLDITALICS(text) }
  }

  def citation: Parser[CITATION] = {
    "@cite\\{([^}]+)\\}*".r ^^ { citation => CITATION(citation) }
  }

  def references: Parser[REFERENCE] = {
    "@ref\\{([^}]+)\\}".r ^^ { label => REFERENCE(label) }
  }

}

//boldItalics | bold | italics | citation | references | layoutSection | section | subsection,
object TestSimpleTexLexer extends SimpleTexLexer {
  def main(args: Array[String]) = {
    parse(citation, "@cite{thisthing}@cite{thisotherhitng}") match {
      case Success(matched, _) => println(matched)
      case Failure(msg, _)     => println("FAILURE: " + msg)
      case Error(msg, _)       => println("ERROR: " + msg)
    }
  }
}
