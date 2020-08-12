/**
  * This file contains the parser
  */

package simpletex.parser
import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.{NoPosition, Position, Reader}
import simpletex.lexer._

object SimpleTexParser extends Parsers {
  override type Elem = SimpleTexToken
  class SimpleTexTokenReader(tokens: Seq[SimpleTexToken])
      extends Reader[SimpleTexToken] {
    override def first: SimpleTexToken = tokens.head
    override def atEnd: Boolean = tokens.isEmpty
    override def pos: Position = NoPosition
    override def rest: Reader[SimpleTexToken] =
      new SimpleTexTokenReader(tokens.tail)
  }

}

