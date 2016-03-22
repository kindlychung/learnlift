package code.snippet

import net.liftweb.http._
import net.liftweb.util._
import code.lib.SendEmail
import java.util.regex.Pattern
import xml.Text

/**
  * User: colt44
  * Date: 1/19/13
  * Time: 11:57 AM
  */
class SinglePageForm extends LiftScreen {
  val emailRegex = Pattern.compile("\\b[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b")

  def minNumOfWords(num: => Int, msg: => String): String => List[FieldError] =
    s => s match {
      case str if (str != null) && str.split(" ").size >= num => Nil
      case _ => List(FieldError(currentField.box openOr new FieldIdentifier {}, Text(msg)))
    }

  val from = field(
    "E-mail",
    "",
    "placeholder" -> "Enter your e-mail",
    valRegex(emailRegex, "Invalid e-mail")
  )

  val subject = field(
    "Subject",
    "",
    "placeholder" -> "Enter the subject of your message",
    valMinLen(10, "Subject too short"),
    valMaxLen(140, "Subject too long")
  )

  val body = field(
    "Message",
    "",
    "placeholder" -> "Enter your message",
    valMinLen(20, "Message too short"),
    valMaxLen(400, "Message too long"),
    minNumOfWords(3, "The e-mail body should have at least three words")
  )

  protected def finish() {
    S.notice("form submitted")

    SendEmail.send_!(
      from,
      "to_email@example.com",
      subject,
      body
    )
  }
}
