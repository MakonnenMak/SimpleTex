/**
  * This file contains the parser
  */
package simpletex.parser
import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.{NoPosition, Position, Reader}
import simpletex.lexer._
import simpletex.parser._

object SimpleTexParser extends Parsers {
  override type Elem = SimpleTexToken
  class SimpleTexTokenReader(tokens: Seq[SimpleTexToken])
      extends Reader[SimpleTexToken] {
    override def first: SimpleTexToken = tokens.head
    override def atEnd: Boolean = tokens.isEmpty
    override def pos: Position = NoPosition
    override def rest: Reader[SimpleTexToken] =
      new SimpleTexTokenReader(tokens.tail)
  }

  def apply(tokens: Seq[SimpleTexToken]): Either[String, SimpleTexAST] = {
    val reader = new SimpleTexTokenReader(tokens)
    document(reader) match {
      case NoSuccess(msg, next)  => Left(msg)
      case Success(result, next) => Right(result)
    }
  }

  def document: Parser[SimpleTexAST] = {
    phrase(block)
  }

  def block: Parser[SimpleTexAST] = {
    rep1(sections) ^^ {
      case sectionList => Document(sectionList)
    }
  }

  def sections: Parser[SimpleTexAST] = {

    val subsections =
      SUBSECTION() ~ plaintext ~ NEWLINE() ~ rep(content) ^^ {
        case _ ~ title ~ _ ~ content => Subsection(title, content)
      }
    val section =
      SECTION() ~ plaintext ~ NEWLINE() ~ rep(subsections) ~ rep(content) ^^ {
        case _ ~ title ~ _ ~ subsections ~ content =>
          Section(title, subsections, content)
      }

    val plainbody = rep1(content) ^^ {
      case plaincontent => PlainBody(plaincontent)
    }

    val layoutsection = LAYOUT() ~ plaintext ~ NEWLINE() ~ section ^^ {
      case _ ~ layout ~ _ ~ section => LayoutSection(layout, section)
    }
    section | plainbody | layoutsection
  }

  val plaintext =
    rep1(text) ^^ { case text => PlainText(text.map(_.value)) }

  def content: Parser[Content] = {
    val bold = BOLDL() ~ plaintext ~ BOLDR() ^^ {
      case _ ~ plaintext ~ _ => Bold(plaintext)
    }

    val italics = ITALICSL() ~ plaintext ~ ITALICSR() ^^ {
      case _ ~ plaintext ~ _ => Italics(plaintext)
    }

    val bolditalics = BOLDITALICSL() ~ plaintext ~ BOLDITALICSR() ^^ {
      case _ ~ plaintext ~ _ => BoldItalics(plaintext)
    }

    val citation = CITATION() ~ BRACEL() ~ plaintext ~ BRACER() ^^ {
      case _ ~ _ ~ plaintext ~ _ => Citation(plaintext)
    }

    val reference = REFERENCE() ~ BRACEL() ~ plaintext ~ BRACER() ^^ {
      case _ ~ _ ~ plaintext ~ _ => Reference(plaintext)
    }

    val image =
      EXSQUARE() ~ plaintext ~ SQUARER() ~ PARENL() ~ plaintext ~ PARENR() ^^ {
        case _ ~ caption ~ _ ~ _ ~ path ~ _ => Image(caption, path)
      }

    bold | plaintext | italics | bolditalics | citation | reference | image
  }

  def label: Parser[Annotations] =
    LABEL() ~ BRACEL() ~ plaintext ~ BRACER() ^^ {
      case _ ~ _ ~ plaintext ~ _ => Label(plaintext)
    }

  private def text: Parser[TEXT] = {
    accept("string text", { case text @ TEXT(word) => text })
  }

}
