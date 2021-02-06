package simpletex.generator
import simpletex.generator.Document
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
  ): Either[SimpleTexCompilationError, Document] = {
    val doc = Document(layout)
    Left(SimpleTexGeneratorError("not implemented"))
  }

}
