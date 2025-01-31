package simpletex.parser;
import org.scalatest.funsuite.AnyFunSuite

import simpletex.lexer._;
import simpletex.parser._;

class ParserSectionIntegration extends AnyFunSuite {

  test("Section with bold content parses") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("hi"),
        TEXT("world"),
        NEWLINE(),
        BOLDL(),
        TEXT("hello"),
        TEXT("world"),
        TEXT("hi"),
        BOLDR()
      )
    ) match {
      case Left(value)  => fail(s"We didn't parse this correctly: $value")
      case Right(value) => assert(true)
    }
  }
  test("section with plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        TEXT("my"),
        TEXT("section"),
        NEWLINE(),
        TEXT("hello"),
        TEXT("world")
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
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
              )
            )
          )
          ) =>
        assert(true)
      case Right(other) => fail(s"We didn't parse this correctly: $other")
    }
  }
  test("section with large plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        TEXT("my"),
        TEXT("section"),
        NEWLINE(),
        TEXT("this"),
        TEXT("is"),
        TEXT("a"),
        TEXT("large"),
        TEXT("amount"),
        TEXT("of"),
        TEXT("plaintext"),
        TEXT("i"),
        TEXT("think")
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(),
                List(
                  PlainText(List("my", "section")),
                  Newline(),
                  PlainText(
                    List(
                      "this",
                      "is",
                      "a",
                      "large",
                      "amount",
                      "of",
                      "plaintext",
                      "i",
                      "think"
                    )
                  )
                )
              )
            )
          )
          ) =>
        assert(true)
      case Right(other) => fail(s"We didn't parse this correctly: $other")
    }
  }
  test("section with italics text and plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        TEXT("hello"),
        ITALICSL(),
        TEXT("world"),
        ITALICSR()
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(),
                List(
                  PlainText(List("hello")),
                  Italics(PlainText(List("world")))
                )
              )
            )
          )
          ) =>
        assert(true)
      case Right(value) => fail(s"we parsed this to something else: $value")
    }
  }
  test("section with bolditalics text and plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        TEXT("hello"),
        BOLDITALICSL(),
        TEXT("world"),
        BOLDITALICSR()
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(),
                List(
                  PlainText(List("hello")),
                  BoldItalics(PlainText(List("world")))
                )
              )
            )
          )
          ) =>
        assert(true)
      case Right(value) => fail(s"we parsed into something else $value")
    }
  }
  test("section with citations and plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        TEXT("hello"),
        TEXT("world"),
        CITATION(),
        BRACEL(),
        TEXT("Some"),
        TEXT("Citation"),
        BRACER()
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(),
                List(
                  PlainText(List("hello", "world")),
                  Citation(PlainText(List("Some", "Citation")))
                )
              )
            )
          )
          ) =>
        assert(true)
      case Right(value) => fail(s"We parsed this into something else $value")
    }
  }
  test("section with references and plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        TEXT("hello"),
        TEXT("world"),
        REFERENCE(),
        BRACEL(),
        TEXT("Some"),
        TEXT("Reference"),
        BRACER()
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(),
                List(
                  PlainText(List("hello", "world")),
                  Reference(PlainText(List("Some", "Reference")))
                )
              )
            )
          )
          ) =>
        assert(true)
      case Right(value) => fail(s"We parsed this to something else $value")
    }
  }
  test("section with equations and plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        TEXT("hello"),
        TEXT("world"),
        EQUATIONL(),
        TEXT("4x^2+5"),
        EQUATIONR()
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(),
                List(
                  PlainText(List("hello", "world")),
                  Equation(PlainText(List("4x^2+5")))
                )
              )
            )
          )
          ) =>
        assert(true)

      case Right(value) => fail(s"We parsed it to something else: $value")
    }
  }
}

class ParserSectionLayoutIntegration extends AnyFunSuite { //idk what to name the class
  test("Section with layout and a lot of other content") {
    SimpleTexParser(
      Seq(
        LAYOUT(),
        TEXT("l1"),
        NEWLINE(),
        SECTION(),
        TEXT("Intro"),
        TEXT("Section"),
        NEWLINE(),
        BOLDITALICSL(),
        TEXT("hello"),
        TEXT("world"),
        BOLDITALICSR(), //FIX: adding a new line under here will break this test
        TEXT("this"),
        TEXT("is"),
        TEXT("an"),
        BOLDL(),
        TEXT("important"),
        BOLDR(),
        ITALICSL(),
        TEXT("paper"),
        ITALICSR(),
        TEXT("equations"),
        EQUATIONL(),
        TEXT("4x^2+5"),
        EQUATIONR(),
        TEXT("citations"),
        CITATION(),
        BRACEL(),
        TEXT("Some"),
        TEXT("Citation"),
        BRACER(),
        TEXT("Some"),
        TEXT("Reference"),
        REFERENCE(),
        BRACEL(),
        TEXT("Some"),
        TEXT("Reference"),
        BRACER()
      )
    ) match {
      case Left(value) =>
        fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              LayoutSection(
                PlainText(List("l1")),
                Section(
                  PlainText(List("Intro", "Section")),
                  List(),
                  List(
                    BoldItalics(PlainText(List("hello", "world"))),
                    PlainText(List("this", "is", "an")),
                    Bold(PlainText(List("important"))),
                    Italics(PlainText(List("paper"))),
                    PlainText(List("equations")),
                    Equation(PlainText(List("4x^2+5"))),
                    PlainText(List("citations")),
                    Citation(PlainText(List("Some", "Citation"))),
                    PlainText(List("Some", "Reference")),
                    Reference(PlainText(List("Some", "Reference")))
                  )
                )
              )
            )
          )
          ) =>
        assert(true)
      case Right(other) =>
        fail(s"We didn't parse this correctly: $other")
    }
  }
}
class ParserSubsectionIntegration extends AnyFunSuite {
  test("subsection with plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        SUBSECTION(),
        TEXT("my"),
        TEXT("subsection"),
        NEWLINE(),
        TEXT("hello"),
        TEXT("world")
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(
                  Subsection(
                    PlainText(List("my", "subsection")),
                    List(PlainText(List("hello", "world")))
                  )
                ),
                List()
              )
            )
          )
          ) =>
        assert(true)
      case Right(other) => fail(s"We didn't parse this correctly: $other")
    }
  }
  test("subsection with large plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        SUBSECTION(),
        TEXT("my"),
        TEXT("subsection"),
        NEWLINE(),
        TEXT("this"),
        TEXT("is"),
        TEXT("a"),
        TEXT("large"),
        TEXT("amount"),
        TEXT("of"),
        TEXT("plaintext"),
        TEXT("i"),
        TEXT("think")
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(
                  Subsection(
                    PlainText(List("my", "subsection")),
                    List(
                      PlainText(
                        List(
                          "this",
                          "is",
                          "a",
                          "large",
                          "amount",
                          "of",
                          "plaintext",
                          "i",
                          "think"
                        )
                      )
                    )
                  )
                ),
                List()
              )
            )
          )
          ) =>
        assert(true)
      case Right(other) => fail(s"We didn't parse this correctly: $other")
    }
  }
  test("subsection with bold text and plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        SUBSECTION(),
        TEXT("my"),
        TEXT("subsection"),
        NEWLINE(),
        TEXT("hello"),
        BOLDL(),
        TEXT("world"),
        BOLDR()
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(
                  Subsection(
                    PlainText(List("my", "subsection")),
                    List(
                      PlainText(List("hello")),
                      Bold(PlainText(List("world")))
                    )
                  )
                ),
                List()
              )
            )
          )
          ) =>
        assert(true)
      case Right(other) => fail(s"We didn't parse this correctly: $other")
    }
  }
  test("subsection with italics text and plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        SUBSECTION(),
        TEXT("my"),
        TEXT("subsection"),
        NEWLINE(),
        TEXT("hello"),
        ITALICSL(),
        TEXT("world"),
        ITALICSR()
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(
                  Subsection(
                    PlainText(List("my", "subsection")),
                    List(
                      PlainText(List("hello")),
                      Italics(PlainText(List("world")))
                    )
                  )
                ),
                List()
              )
            )
          )
          ) =>
        assert(true)
      case Right(value) => fail(s"we parsed this to something else: $value")
    }
  }
  test("subsection with bolditalics text and plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        SUBSECTION(),
        TEXT("my"),
        TEXT("subsection"),
        NEWLINE(),
        TEXT("hello"),
        BOLDITALICSL(),
        TEXT("world"),
        BOLDITALICSR()
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(
                  Subsection(
                    PlainText(List("my", "subsection")),
                    List(
                      PlainText(List("hello")),
                      BoldItalics(PlainText(List("world")))
                    )
                  )
                ),
                List()
              )
            )
          )
          ) =>
        assert(true)
      case Right(value) => fail(s"we parsed into something else $value")
    }
  }
  test("subsection with citations and plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        SUBSECTION(),
        TEXT("my"),
        TEXT("subsection"),
        NEWLINE(),
        TEXT("hello"),
        TEXT("world"),
        CITATION(),
        BRACEL(),
        TEXT("Some"),
        TEXT("Citation"),
        BRACER()
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(
                  Subsection(
                    PlainText(List("my", "subsection")),
                    List(
                      PlainText(List("hello", "world")),
                      Citation(PlainText(List("Some", "Citation")))
                    )
                  )
                ),
                List()
              )
            )
          )
          ) =>
        assert(true)
      case Right(value) => fail(s"We parsed this into something else $value")
    }
  }
  test("subsection with references and plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        SUBSECTION(),
        TEXT("my"),
        TEXT("subsection"),
        NEWLINE(),
        TEXT("hello"),
        TEXT("world"),
        REFERENCE(),
        BRACEL(),
        TEXT("Some"),
        TEXT("Reference"),
        BRACER()
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(
                  Subsection(
                    PlainText(List("my", "subsection")),
                    List(
                      PlainText(List("hello", "world")),
                      Reference(PlainText(List("Some", "Reference")))
                    )
                  )
                ),
                List()
              )
            )
          )
          ) =>
        assert(true)
      case Right(value) => fail(s"We parsed this to something else $value")
    }
  }
  test("subsection with equations and plaintext") {
    SimpleTexParser(
      Seq(
        SECTION(),
        TEXT("Section"),
        NEWLINE(),
        SUBSECTION(),
        TEXT("my"),
        TEXT("subsection"),
        NEWLINE(),
        TEXT("hello"),
        TEXT("world"),
        EQUATIONL(),
        TEXT("4x^2+5"),
        EQUATIONR()
      )
    ) match {
      case Left(value) => fail(s"We didn't parse this correctly: $value")
      case Right(
          Document(
            List(
              Section(
                PlainText(List("Section")),
                List(
                  Subsection(
                    PlainText(List("my", "subsection")),
                    List(
                      PlainText(List("hello", "world")),
                      Equation(PlainText(List("4x^2+5")))
                    )
                  )
                ),
                List()
              )
            )
          )
          ) =>
        assert(true)

      case Right(value) => fail(s"We parsed it to something else: $value")
    }
  }
}
