package simpletex.generator
import simpletex.generator.Document
import simpletex.compiler.{
  SimpleTexCompiler,
  SimpleTexCompilationError,
  SimpleTexGeneratorError
}
import simpletex.parser.SimpleTexAST

object DocumentGenerator {
  def apply(ast: SimpleTexAST): Either[SimpleTexCompilationError, Document] = {
    Left(SimpleTexGeneratorError("not implemented"))
  }

}
