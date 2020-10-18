package simpletex.parser
import scala.util.parsing.input.Positional

sealed trait SimpleTexAST
case class Document(
    body: Seq[SimpleTexAST]
) extends SimpleTexAST
case class Section(
    name: PlainText,
    subsection: Seq[Subsection],
    content: Seq[Content]
) extends SimpleTexAST
case class Subsection(name: PlainText, content: Seq[Content])
    extends SimpleTexAST
case class PlainBody(content: Seq[Content]) extends SimpleTexAST
case class LayoutSection(name: PlainText, section: Section) extends SimpleTexAST

sealed trait Content
case class PlainText(text: Seq[String]) extends Content
case class Bold(text: PlainText) extends Content
case class Italics(text: PlainText) extends Content
case class BoldItalics(text: PlainText) extends Content
case class Citation(text: PlainText) extends Content
case class Reference(text: PlainText) extends Content
case class Image(caption: PlainText, path: PlainText) extends Content
case class Equation(text: PlainText) extends Content
case class Newline() extends Content

sealed trait Annotations
case class Label(value: PlainText) extends Annotations
