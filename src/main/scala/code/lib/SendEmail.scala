package code.lib

import net.liftweb.util.Mailer
import net.liftweb.util.Mailer.{From, PlainMailBodyType, Subject, To}

/**
 * Created by IDEA on 29/10/15.
 */
object SendEmail {
  def send_!(from: String, recipient: String, subject: String, body: String): Unit = {
    val mailTypes = List(PlainMailBodyType(body), To(recipient))
    Mailer.msgSendImpl(From(from), Subject(subject), mailTypes)
  }
}
