package code.snippet

import net.liftweb.common.Logger
import net.liftweb.util.CssSel
import net.liftweb.util.Helpers._

import scala.xml.Text

/**
 * Created by IDEA on 28/10/15.
 */
class ListUser extends Logger {
  def log(text: String): Unit = {
    text match {
      case str if str.length == 0 => error("user with no name")
      case str if str == "Forbidden" => warn("this user shouldn't have access")
      case str => debug("User name: " + str)
    }
  }
  def list: CssSel = {
    val users = List("John", "Sarah", "Peter", "Sam", "", "Forbidden")
    info("listing users")
    "li .name *" #> users.map {
      user => {
        log(user)
        Text(user)
      }
    }
  }
}
