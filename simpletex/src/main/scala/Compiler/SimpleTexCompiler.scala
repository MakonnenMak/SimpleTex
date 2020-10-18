package simpletex.compiler

import simpletex.lexer.SimpleTexLexer
import simpletex.parser.{SimpleTexParser, SimpleTexAST}

object SimpleTexCompiler {
  def apply(code: String): Either[SimpleTexCompilationError, SimpleTexAST] = {
    for {
      tokens <- SimpleTexLexer(code).right
      ast <- SimpleTexParser(tokens).right
    } yield ast
  }

}
