package simpletex.compiler;
import org.scalatest.funsuite.AnyFunSuite

import simpletex.compiler.SimpleTexCompiler
import simpletex.generator.{DocumentGenerator, LatexDocument}
import simpletex.parser._
import simpletex.generator.Layout

/** https://www.notion.so/Latex-Generation-7f35586896434ee4bbe72aaf8b684b81?p=00a71ae950884d9ebaf3356bc3045ba6
  */
class GeneratorTests extends AnyFunSuite {
  test("") {
    val testLayout =
      Layout(
        "layout1",
        List(50, 50),
        List(50, 50),
        List(List("1", "2"), List("3", "4"))
      )
    val doc = DocumentGenerator(
      Document(
        List(
          LayoutSection(
            PlainText(Seq("layout1.name1")),
            Section(
              PlainText(List("Section")),
              List(),
              List(
                PlainText(List("my", "section")),
                Newline(),
                PlainText(List("hello", "world"))
              )
            )
          ),
          LayoutSection(
            PlainText(Seq("layout1.name2")),
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
        )
      ),
      List(testLayout)
    )

    fail(s"failed with: ${doc.generateDocument()}")
  }

}
