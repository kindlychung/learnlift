package code.snippet

import net.liftweb.util.BindHelpers._
import net.liftweb.http.S

/**
  * User: colt44
  * Date: 2/3/13
  * Time: 10:58 AM
  */
class Localization {
  def dynamic = {
    "*" #> S.?("dynamic.text")
  }
}
