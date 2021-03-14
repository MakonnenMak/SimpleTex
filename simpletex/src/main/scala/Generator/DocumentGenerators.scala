package simpletex.generator
import simpletex.generator.LatexDocument
import simpletex.compiler.{
  SimpleTexCompiler,
  SimpleTexCompilationError,
  SimpleTexGeneratorError
}
import simpletex.parser._

object DocumentGenerator {
  def apply(
      ast: SimpleTexAST,
      layout: List[Layout]
  ): LatexDocument = {
    val doc = LatexDocument(layout)
    val generatedLatexAST = generateAST(doc)(ast);
    //println(generatedLatexAST)
    doc.generateDocument()
    doc
  }
  def generateContent(node: Content): String =
    node match {
      case PlainText(text) => text.mkString(" ")
      case Bold(text)      => s"\textbf{${generateContent(text)}}"
      case Italics(text)   => s"\textit{${generateContent(text)}}"
      case BoldItalics(text) =>
        generateContent(Bold(PlainText(Seq(generateContent(Italics(text))))))
      case Citation(text)  => s"\\cite{${generateContent(text)}}"
      case Reference(text) => s"\\ref${generateContent(text)}}"
      case Image(caption, path) =>
        s"\\begin{figure}[h] \\centering \\includegraphics[width=0.8\\linewidth]{${generateContent(path)}} \\caption{${generateContent(caption)}}% \\label{fig:{caption.subString(0,5)}} \\end{figure}"
      case Equation(text) => "$" + generateContent(text) + "$"
      case Newline()      => "\n" //TODO do we want an empty line or just a new line?
    }
  def generateAST(doc: LatexDocument)(node: SimpleTexAST): String = {
    val generator: SimpleTexAST => String = generateAST(doc)
    node match {
      case Document(body) =>
        body
          .map(generator(_))
          .mkString // do we want to convert to a string this way
      case Section(name, subsection, content) =>
        val subs = subsection.map(generator(_)).mkString
        val sectionBody: String =
          content.map(generateContent(_)).mkString + "\n"
        "\\section{" + generateContent(name) + "}\n" + sectionBody + subs
      case Subsection(name, content) =>
        "\\subsection{" + name + "}" + content
          .map(generateContent(_))
          .mkString + "\n"
      case PlainBody(content) =>
        content.map(generateContent(_)).mkString
      case LayoutSection(name, section) =>
        generateContent(name).split("\\.") match {
          case Array(a, b) => doc.update(a, b, generator(section))
          case array       => s"WRONG TYPE: $array"
        }
    }
  }

}
