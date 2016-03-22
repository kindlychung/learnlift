package code.snippet

import net.liftweb.util.Helpers._

object Table {
  def dynamic = {
    val headers = (1 to 10)
    // creates a 10 x 10 matrix
    val table = headers.map(n => (1 to 10).map(in => n *
      in))
    "th *" #> headers &
      "tbody tr *" #> table.map {
        r => {
          "td *" #> r
        }
      }
  }
}

