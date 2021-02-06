package simpletex.compiler;
import org.scalatest.funsuite.AnyFunSuite

import simpletex.compiler.{SimpleTexCompiler, DocumentGenerator, Document}
import simpletex.parser._
import simpletex.generator.Layout

/** https://www.notion.so/Latex-Generation-7f35586896434ee4bbe72aaf8b684b81?p=00a71ae950884d9ebaf3356bc3045ba6
  */

class GeneratorTests extends AnyFunSuite {
  test("ProcessLayout Basic Test") {
    val testLayout =
      Layout("layout1", List(50, 50), List(50, 50), List("1", "2", "3", "4"))
    DocumentGenerator(
      Document(
        List(
          Section(
            PlainText(List("Section")),
            List(),
            List(
              PlainText(List("my", "section")),
              Newline(),
              PlainText(List("hello", "world"))
            )
          ),
          Section(
            PlainText(List("Section 2")),
            List(),
            List(
              PlainText(List("our", "section")),
              Newline(),
              PlainText(List("hello", "hello"))
            )
          )
        )
      ),
      testLayout
    ) match {
      case Left(e) =>
        fail(s"took a left: $e")
      case Right(e) => println(e)
    }
  }

}
