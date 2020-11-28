/* Document representation
 *
 * The document contains a set of layouts. Exposes a way to update a specific cell in a specific layout, which is used by the code generating functions. Eventually, the user calls `generateDocument` to generate the latex document.
 */

package Generator

class Document(layout: String) {
  override def toString = "Some document place holder"

  def update(cellid:Int, content:String): Unit =???
  def generateDocument():String = ???
}
