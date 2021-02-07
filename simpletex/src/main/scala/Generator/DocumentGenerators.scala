package simpletex.generator
import simpletex.generator.LatexDocument
import simpletex.compiler.{
  SimpleTexCompiler,
  SimpleTexCompilationError,
  SimpleTexGeneratorError
}
import simpletex.parser.SimpleTexAST

object DocumentGenerator {
  def apply(
      ast: SimpleTexAST,
      layout: List[Layout]
  ): Either[SimpleTexCompilationError, LatexDocument] = {
    val doc = LatexDocument(layout)
    Left(SimpleTexGeneratorError("not implemented"))
  }

}
