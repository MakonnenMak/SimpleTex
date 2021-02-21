package simpletex.generator
import simpletex.generator.{LatexDocument, LatexConstant}
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

  private def joinWithBraces(
      construct: String,
      content: String
  ): String =
    LatexConstant.lBrace + content.mkString(" ") + LatexConstant.rBrace

  def generateContent(node: Content): String = node match {
    case PlainText(text) => text.mkString
    case Bold(text)      => joinWithBraces("\textbf", generateContent(text))
    case Italics(text)   => joinWithBraces("\textit", generateContent(text))
    case BoldItalics(text) =>
      generateContent(Bold(PlainText(Seq(generateContent(Italics(text))))))
    case Citation(text)  => joinWithBraces("""\cite""", generateContent(text))
    case Reference(text) => joinWithBraces("""\ref""", generateContent(text))
    case Image(caption, path) =>
      s"\\begin{figure}[h] \\centering \\includegraphics[width=0.8\\linewidth]{${generateContent(path)}} \\caption{${generateContent(caption)}}% \\label{fig:{caption.subString(0,5)}} \\end{figure}"
    case Equation(text) => "$" + generateContent(text) + "$"
    case Newline()      => "\n" //TODO do we want an empty line or just a new line?
  }
  def apply(node: SimpleTexAST): String = node match {
    case Document(body) =>
      body
        .map(Generator(_))
        .mkString // do we want to convert to a string this way
    case Section(name, subsection, content) =>
      // TODO add in the section name properly
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
