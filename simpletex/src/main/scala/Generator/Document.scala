/* Document representation
 *
 * The document contains a set of layouts. Exposes a way to update a specific cell in a specific layout, which is used by the code generating functions. Eventually, the user calls `generateDocument` to generate the latex document.
 */
package simpletex.generator

import collection.mutable.Map
import collection.mutable.Queue
import simpletex.compiler.{SimpleTexCompilationError, SimpleTexGeneratorError}

//TODO define layout class that has map: cell -> string
case class LatexDocument(layout: List[Layout]) {
  override def toString = "Some document place holder"
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
  def generateDocument(): Either[SimpleTexGeneratorError, String] = {
    Left(SimpleTexGeneratorError("Generate document faied"))
  }
  // ast map (BOLD => "this is bold") ==> all bold nodes should be "this..."
  // ast map (Section => "section ... children") ==> all sections and their children pass through the function
  //      ^^^ subsection should never pass through the function
  fillLayoutKeys()

  /* Called by `generateDocument` to process a single layout object.
   */
  private def processLayout(
      layout: String
  ): Either[SimpleTexCompilationError, String] = {
    Left(SimpleTexGeneratorError("not implemented"))
  }
}
