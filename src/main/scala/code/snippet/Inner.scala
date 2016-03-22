package code.snippet

import net.liftweb.util.Helpers._

/**
  * User: colt44
  * Date: 1/27/13
  * Time: 10:21 AM
  */
object Inner {
  def logged = {
    "div *" #> "Should only be visible when user is logged in"
  }

  def nonlogged = {
    "div *" #> "Should only be visible when user is not logged in"
  }
}

