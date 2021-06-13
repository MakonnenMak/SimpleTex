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

  def convertCol(): List[Int] = colSizes.map(x => colSizes.size())
  def convertRow(): List[Int] = rowSizes.map(x => (1 / x) * 100)
// (x / sum(x)) * 100
//  33  33  33
// .5   .25  .25
//  2    1    1
//  list.size
//
//
//  .5    .5
//  1      1
//  |  .5   | .5  ---> colSizes.size --> number of cols
//
//  12 # of cols
//  .5   .5
//  6    6  <-- 12 * .5
//  12 * .33
//
//  .5 25 25 -> 3
//  3*.5 --> 1.5 --> 2
//  3* .25 --> .75 -->1
//  3* .25 --> .775 --> 1

}
