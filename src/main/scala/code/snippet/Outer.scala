package code.snippet

import net.liftweb.util.Helpers._
import net.liftweb.http.S
import net.liftweb.common.Full

/**
  * Created by IDEA on 3/14/16.
  */
object Outer {
  def choose = {
    val loggedIn = S.param("loggedin").flatMap(asBoolean)

    loggedIn match {
      case Full(b) if b => ".inner-div [class+]" #> "lift:Inner.logged"
      case _ => ".inner-div [class+]" #> "lift:Inner.nonlogged"
    }
  }
}
