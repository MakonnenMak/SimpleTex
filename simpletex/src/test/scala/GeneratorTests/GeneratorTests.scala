package simpletex.compiler;
import org.scalatest.funsuite.AnyFunSuite

import simpletex.compiler.SimpleTexCompiler
import simpletex.generator.{DocumentGenerator, LatexDocument}
import simpletex.parser._
import simpletex.generator.Layout

/** https://www.notion.so/Latex-Generation-7f35586896434ee4bbe72aaf8b684b81?p=00a71ae950884d9ebaf3356bc3045ba6
  */
class GeneratorTests extends AnyFunSuite {
  test("Latex code should match the expected literal string") {
    val testLayout =
      Layout(
        "layout1",
        List(2, 2),
        List(2, 2),
        List(List("name1", "name2"), List("name3", "name4"))
      )
    val testLayout2 =
      Layout(
        "layout2",
        List(2, 2),
        List(2, 2),
        List(List("name1", "name2"), List("name3", "name4"))
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
          ),
          LayoutSection(
            PlainText(Seq("layout2.name1")),
            Section(
              PlainText(List("New Section")),
              List(),
              List(
                PlainText(List("my", "new", "section")),
                Newline(),
                PlainText(List("hello", "world", "earth"))
              )
            )
          )
        )
      ),
      List(testLayout, testLayout2)
    )

    val expected =
      """\begin {Row} \begin {Cell}{2} \section{Section} my section hello world \end {Cell} \begin {Cell}{2} \section{Section 2} our section hello hello \end {Cell} \begin {Cell}{2} \end {Cell} \begin {Cell}{2} \end {Cell} \end {Row} \begin {Row} \begin {Cell}{2} \section{New Section} my new section hello world earth \end {Cell} \begin {Cell}{2} \end {Cell} \begin {Cell}{2} \end {Cell} \begin {Cell}{2} \end {Cell} \end {Row}"""
        .replaceAll("\n", "")
        .replaceAll(" ", "")

    doc.generateDocument() match {
      case Left(value) => fail(s"Got a left: ${value}")
      case Right(value) =>
        assert(
          value
            .replaceAll("\n", "")
            .replaceAll(" ", "")
            == expected,
          "Got a string but not the expected result."
        )
    }
  }

}
