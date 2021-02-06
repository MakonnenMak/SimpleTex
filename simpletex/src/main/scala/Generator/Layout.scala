/* Layout Representation
 * The layout represents the number of columns, rows, and their respective cell names
 */

package simpletex.generator
case class Layout(
    name: String,
    colSizes: List[Int],
    rowSizes: List[Int],
    cellNames: List[List[String]]
) {

  override def toString(): String =
    f"Name: ${name} \n Columns: ${colSizes} \n Rows: ${rowSizes} \n Cell Names: ${cellNames}"

}
