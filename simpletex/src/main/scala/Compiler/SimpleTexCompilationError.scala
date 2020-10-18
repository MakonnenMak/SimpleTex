package simpletex.compiler

sealed trait SimpleTexCompilationError

case class SimpleTexLexerError(msg: String) extends SimpleTexCompilationError;
case class SimpleTexParserError(msg: String) extends SimpleTexCompilationError;
