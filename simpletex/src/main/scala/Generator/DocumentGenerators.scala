package simpletex.generator
import simpletex.generator.Document
import simpletex.compiler.SimpleTexCompiler
import simpletex.parser.SimpleTexAST

object DocumentGenerator {
  def apply(ast: SimpleTexAST): Either[String, Document] = {
    Left("Not implemented")
  }

}
