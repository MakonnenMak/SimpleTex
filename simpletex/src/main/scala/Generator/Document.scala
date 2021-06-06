/* Document representation
 *
 * The document contains a set of layouts. Exposes a way to update a specific cell in a specific layout, which is used by the code generating functions. Eventually, the user calls `generateDocument` to generate the latex document.
 */
package simpletex.generator

import collection.mutable.{Map, ArrayBuffer}
import collection.mutable.Queue
import simpletex.compiler.{SimpleTexCompilationError, SimpleTexGeneratorError}

//TODO define layout class that has map: cell -> string
case class LatexDocument(layout: List[Layout]) {
  private val layouts: Map[String, Map[String, String]] = Map()
  private val accessQueue: Queue[String] = Queue()
  def fillLayoutKeys(): Unit = {
    layout.foreach(l => layouts.put(l.name, Map()))
  }

  def update(layoutID: String, cellID: String, content: String): String = {
    layouts.get(layoutID).flatMap(l => l.put(cellID, content))
    accessQueue.find(x => x == layoutID) match {
      case Some(value) => None
      case None        => accessQueue enqueue layoutID
    }
    content
  }
  // TODO rewrite this ugly shit
  def generateDocument(): Either[SimpleTexGeneratorError, String] = {
    layouts
      .map({ case (k, v) => processLayout(k, v) })
      .partitionMap {
        case Left(error) => Left(error)
        case Right(str)  => Right(str)
      } match {
      case (ArrayBuffer(), b) =>
        Right(
          b.reduce((a, c) => a + c)
        ) //TODO where is the second right hello???
      case (a, b) => Left(SimpleTexGeneratorError(s"something went wrong: $a"))
    }

  }
  // ast map (BOLD => "this is bold") ==> all bold nodes should be "this..."
  // ast map (Section => "section ... children") ==> all sections and their children pass through the function
  //      ^^^ subsection should never pass through the function
  fillLayoutKeys()

  private def generateCell(col:List[String], cell:Map[String,String]):List[String]={
            col.map(content =>
              s"\n \\begin {Cell}  ${content}\n ${cell.get(content) match {
                case Some(value) => value
                case None        => ""
              }}" + s"\n \\end {Cell} ${content}\n"
            )
  }

  /* Called by `generateDocument` to process a single layout object.
   */
  private def processLayout(
      layoutName: String,
      cell: Map[String, String]
  ): Either[SimpleTexCompilationError, String] = {
    val latex = layout.find(a => a.name == layoutName) match {
      case Some(row) =>
        "\\begin {Row}"+
        s"\n col ${row.colSizes} row ${row.rowSizes}" +
          row.cellNames.flatMap(col =>
              generateCell(col,cell)
              ).mkString +
        "\\end {Row}"
      case None => ""
    }

    println(s"CELL: ${cell.mkString}")
    Right(latex)

  }
}
