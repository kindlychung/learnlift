package code.snippet

import net.liftweb._
import http._
import wizard._
import java.util.regex.Pattern
import scala.language.reflectiveCalls

/**
  * User: colt44
  * Date: 1/19/13
  * Time: 11:57 AM
  */
object MultiPageForm extends Wizard {
  val emailRegex = Pattern.compile("\\b[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b")

  val userData = new Screen {
    val name = field(
      "Name", "",
      valMinLen(10, "Name too short"),
      valMaxLen(140, "Name too long")
    )
    val email = field("E-mail", "", valRegex(emailRegex, "Invalid e-mail"))

    val hasAddress = field("I already have an address", false)

    override def nextScreen = if (!hasAddress) addressData else paymentData
  }

  val addressData = new Screen {
    val line1 = field("Address line 1", "")
    val line2 = field("Address line 2", "")
    val line3 = field("Address line 3", "")
    val city = field("City", "")
    val state = field("State", "")
    val zipCode = field("Zip code", "")
  }

  val paymentData = new Screen {
    val creditCardName = field("Credit card name", "")
    val creditCardNumber = field("Credit card number", "")
    val creditCardExpDate = field("Credit card expiration date", "")
  }

  def finish() {
    val name = userData.name.is
    val email = userData.email.is
    val line1 = addressData.line1.is
    val line2 = addressData.line2.is
    val line3 = addressData.line3.is
    val city = addressData.city.is
    val state = addressData.state.is
    val zip = addressData.zipCode.is

    val ccName = paymentData.creditCardName.is
    val ccNumber = paymentData.creditCardNumber.is
    val ccExpDate = paymentData.creditCardExpDate.is

    S.redirectTo("/finish",
      () => {
        S.notice("Name: " + name)
        S.notice("E-mail: " + email)
        S.notice("Address Line 1: " + line1)
        S.notice("Address Line 2: " + line2)
        S.notice("Address Line 3: " + line3)
        S.notice("City: " + city)
        S.notice("State: " + state)
        S.notice("Zip Code: " + zip)
        S.notice("Credit card name: " + ccName)
        S.notice("Credit card number: " + ccNumber)
        S.notice("Credit card expiration date" + ccExpDate)
      }
    )
  }
}
