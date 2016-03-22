package code.snippet

import code.model._
import net.liftweb.util.BindHelpers._
import scala.util.Random

/**
  * User: ggarcia
  * Date: 4/17/13
  * Time: 8:55 PM
  */
class Contacts {

  def prepareContacts_!() {
    Contact.findAll().map(_.delete_!)
    val contactsNames = "John" :: "Joe" :: "Lisa" :: Nil
    val phones = "5555-5555" :: "5555-4444" :: "5555-3333"  :: "5555-2222" :: "5555-1111" :: "5555-0000" :: "5555-6666" :: "5555-7777" :: Nil
    val rand = util.Random
    contactsNames.map(name => {
      val contact = Contact.create.name(name)
      val phone = Phone.create.number(phones(rand.nextInt(5))).saveMe()
      contact.phones.append(phone)
      contact.save()
    })
  }

  def list = {
    prepareContacts_!()

    ".contact *" #> Contact.findAll().map {
      contact => {
        ".name *" #> contact.name.get &
          ".phone *" #> contact.phones.map(_.number.get)
      }
    }
  }
}
