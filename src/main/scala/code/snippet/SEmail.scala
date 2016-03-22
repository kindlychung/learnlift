package code.snippet

import code.lib.SendEmail
import net.liftweb.http.SHtml
import net.liftweb.util.Props
import net.liftweb.util.Helpers._

import scala.xml.Text

/**
 * Created by IDEA on 29/10/15.
 */
class SEmail {
  def sendNow() = {
    SendEmail.send_!(
      Props.get("mail.user").openOrThrowException("Can't get username"),
    "youtube@hotmail.com",
    "Sending an email",
    "Here is from lift!"
    )
  }

  def sendEmail = {
    "*" #> SHtml.link("#", () => sendNow(), Text("Send email"))
  }
}
