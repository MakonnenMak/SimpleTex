package simpletex.generator
import simpletex.generator.LatexDocument
import simpletex.compiler.{
  SimpleTexCompiler,
  SimpleTexCompilationError,
  SimpleTexGeneratorError
}
import simpletex.parser._

object Generator {
  def generateAST(node: SimpleTexAST) = {
    // decide what to do here
  }
  def generateContent(node: Content): String = {
    // pattern match on the content types
    "Not found"
  }
  def apply(node: SimpleTexAST): String = node match {
    case Document(body) =>
      body
        .map(Generator(_))
        .mkString // do we want to convert to a string this way
    case Section(name, subsection, content) =>
      // add in the section name properly
      val subs = subsection.map(Generator(_)).mkString
      val sectionBody: String = content.map(generateContent(_)).mkString
      name + sectionBody + subs
    case Subsection(name, content) =>
      name + content.map(generateContent(_)).mkString
    case PlainBody(content) =>
      content.map(generateContent(_)).mkString
    case LayoutSection(name, section) =>
      name + Generator(section)
  }
}
object DocumentGenerator {
  def apply(
      ast: SimpleTexAST,
      layout: List[Layout]
  ): Either[SimpleTexCompilationError, LatexDocument] = {
    val doc = LatexDocument(layout)
    Left(SimpleTexGeneratorError("not implemented"))
  }

  /* def bold ..
   * def italics ...
   *
   *
   */
  /* def section(...) match
 *
 * bold:  -> bold(content) or produce here \bf ...
 * italics: -> \it...
 *
 *
 *
 *
 */
}
