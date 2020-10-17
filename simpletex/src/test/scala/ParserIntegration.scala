package simpletex.lexer;
import org.scalatest.funsuite.AnyFunSuite

import simpletex.lexer._;
import simpletex.parser._;

class ParserSectionIntegrationLarge extends AnyFunSuite { //idk what to name the class
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
      case Right(value) =>
        assert(true)
    }
  }
}
class ParserSubsectionIntegration extends AnyFunSuite {
  test("some test") {
    assert(true)
  }
}
