/**
  * This file contains the parser
  * package simpletex.parser
  * import scala.util.parsing.combinator.Parsers
  * import scala.util.parsing.input.{NoPosition, Position, Reader}
  * import simpletex.lexer._
  *
  * object SimpleTexParser extends Parsers {
  *  override type Elem = SimpleTexToken
  *  class SimpleTexTokenReader(tokens: Seq[SimpleTexToken])
  *      extends Reader[SimpleTexToken] {
  *    override def first: SimpleTexToken = tokens.head
  *    override def atEnd: Boolean = tokens.isEmpty
  *    override def pos: Position = NoPosition
  *    override def rest: Reader[SimpleTexToken] =
  *      new SimpleTexTokenReader(tokens.tail)
  *  }
  *
  *  def apply(tokens: Seq[SimpleTexToken]): Either[String, SimpleTexAST] = {
  *    val reader = new SimpleTexTokenReader(tokens)
  *    document(reader) match {
  *      case NoSuccess(msg, next)  => Left(msg)
  *      case Success(result, next) => Right(result)
  *    }
  *  }
  *
  *  def document: Parser[SimpleTexAST] = {
  *    phrase(block)
  *  }
  *
  *  def block: Parser[SimpleTexAST] = {
  *    rep1(sections) ^^ {
  *      case stmtList => stmtList
  *    } //TODO reduce/fold to something that is an SimpletexAST type
  *  }
  *
  *  def sections: Parser[SimpletexAST] ={
  *
  *  }
  *  private def text: Parser[LITERAL] = {
  *    accept("string text", { case text @ TEXT(word) => text })
  *  }
  *
  * }
  */
