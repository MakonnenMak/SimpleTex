/* Layout Representation
 * The layout represents the number of columns, rows, and their respective cell names
 */

package Generator
class Layout(
    name: String,
    cols: List[Int],
    rows: List[Int],
    cellNames: List[List[String]]
) {

  override def toString(): String =
    f"Name: ${name} \n Columns: ${cols} \n Rows: ${rows} \n Cell Names: ${cellNames}"

}
