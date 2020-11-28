/* Document representation
 *
 * The document contains a set of layouts. Exposes a way to update a specific cell in a specific layout, which is used by the code generating functions. Eventually, the user calls `generateDocument` to generate the latex document.
 */
package Generator

import collection.mutable.Map
import Generator.Layout

//TODO define layout class that has map: cell -> string
class Document(layout: List[Layout]) {
  override def toString = "Some document place holder"

  def update(layoutID: String, cellID: String, content: String): Unit = {}
  def generateDocument(): String = { "not implemented" }

  private val layouts: Map[String, Layout] = Map()
}
