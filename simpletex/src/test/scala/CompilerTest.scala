package simpletex.compiler;
import org.scalatest.funsuite.AnyFunSuite

import simpletex.compiler.SimpleTexCompiler
import simpletex.parser._

class CompilerBasics extends AnyFunSuite {
  test("should compile") {
    SimpleTexCompiler(
      "# section name \n some content\n ## subsection \n here is some"
    ) match {
      case Left(e) =>
        fail(s"took a left: $e")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("section", "name")),
                List(
                  Subsection(
                    PlainText(List("subsection")),
                    List(PlainText(List("here", "is", "some")))
                  )
                ),
                List(PlainText(List("some", "content")), Newline())
              )
            )
          )
          ) =>
        assert(true)

      case Right(v) => fail(s"We prased something different: $v")
    }
  }

}
