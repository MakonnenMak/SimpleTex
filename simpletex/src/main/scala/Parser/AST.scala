package simpletex.parser
import scala.util.parsing.input.Positional

sealed trait SimpleTexAST
case class Document(
    body: Seq[SimpleTexAST]
) extends SimpleTexAST
case class Section(
    name: Seq[String],
    subsection: Seq[Subsection],
    content: Seq[Content]
) extends SimpleTexAST
case class Subsection(name: Seq[String], content: Seq[Content])
    extends SimpleTexAST
case class LayoutSection(name: String, section: Section) extends SimpleTexAST

sealed trait Content
case class PlainText(text: Seq[String]) extends Content
case class Bold(text: String) extends Content
case class Italics(text: String) extends Content
case class BoldItalics(text: String) extends Content
case class Citation(text: String) extends Content
case class Reference(text: String) extends Content
case class Image(label: String, caption: String, path: String) extends Content

sealed trait Annotations
case class Label(value: String) extends Annotations
