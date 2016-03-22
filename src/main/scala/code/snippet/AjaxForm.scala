package code.snippet

import net.liftweb._
import http._
import js.JsCmds
import JsCmds._
import util.BindHelpers._
import code.lib.SendEmail
import java.util.regex.Pattern

/**
  * User: colt44
  * Date: 1/19/13
  * Time: 11:57 AM
  */
class AjaxForm {
  val emailRegex = Pattern.compile("\\b[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b")

  def form = {
    var from = ""
    var subject = ""
    var message = ""

    def process() = {
      if (!emailRegex.matcher(from).matches()) {
        S.warning("From is not a valid e-mail")
        Noop
      } else if (subject.trim.length < 10 || subject.trim.length > 140) {
        S.warning("Subject should have at least 10 chars and have no more than 140.")
        Noop
      } else if (message.trim.length < 20 || message.trim.length > 400) {
        S.warning("Message should have at least 20 chars and have no more than 400.")
        Noop
      } else {
        SendEmail.send_!(
          from,
          "to_email@example.com",
          subject,
          message
        )

        JsCmds.Alert("Message sent") &
          JsCmds.SetValById("from", "") &
          JsCmds.SetValById("subject", "") &
          JsCmds.SetValById("message", "")
      }
    }

    "#from" #> SHtml.text(from, from = _, "id" -> "from") &
      "#subject" #> (SHtml.text(subject, subject = _, "id" -> "subject")) &
      "#message" #> (SHtml.textarea(message, message = _, "id" -> "message") ++ SHtml.hidden(process))
  }
}
