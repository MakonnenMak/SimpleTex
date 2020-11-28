/* Document representation
 *
 * The document contains a set of layouts. Exposes a way to update a specific cell in a specific layout, which is used by the code generating functions. Eventually, the user calls `generateDocument` to generate the latex document.
 */
package Generator

import collection.mutable.Map

//TODO define layout class that has map: cell -> string
class Document(layout: List[String]) {
  override def toString = "Some document place holder"

  def update(layoutid: Int, cellid: Int, content: String): Unit = {}
  def generateDocument(): String = { "not implemented" }

  //TODO second string is layout objects
  private val layouts: Map[String, String] = Map()
}
