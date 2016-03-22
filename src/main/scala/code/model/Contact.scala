package code.model

import net.liftweb.mapper._

/**
  * User: ggarcia
  * Date: 4/15/13
  * Time: 8:18 PM
  */
class Contact extends LongKeyedMapper[Contact]
  with IdPK
  with OneToMany[Long, Contact]
  with CRUDify[Long, Contact] {
  def getSingleton = Contact

  object name extends MappedString(this, 100)

  object phones extends MappedOneToMany(Phone, Phone.contact, OrderBy(Phone.id, Ascending))
    with Owned[Phone] with Cascade[Phone]

}

object Contact extends Contact with LongKeyedMetaMapper[Contact] {
  override def dbTableName = "contacts"
}