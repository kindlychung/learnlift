package code.snippet

import net.liftweb.util.BindHelpers._

/**
 * Created by IDEA on 29/10/15.
 */
object Calculator {
  def plus = "#result *" #> (2 + 2)
}
