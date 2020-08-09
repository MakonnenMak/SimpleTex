package simpletex.parser
import scala.util.parsing.input.Positional

sealed trait SimpleTexAST
case class FollowedBy(body1: SimpleTexAST, body2: SimpleTexAST)
    extends SimpleTexAST
