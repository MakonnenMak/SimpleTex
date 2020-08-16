package simpletex.parser
import scala.util.parsing.input.Positional

sealed trait SimpleTexAST
case class Section(subsection: Seq[Subsection], content: Seq[Content])
    extends SimpleTexAST
case class Subsection(content: Seq[Content]) extends SimpleTexAST
case class LayoutSection(layoutName: Annotations, section: Section)

sealed trait Content

sealed trait Annotations
