package code.model

import net.liftweb.mapper._

/**
  * User: ggarcia
  * Date: 4/19/13
  * Time: 10:09 PM
  */
class Phone extends LongKeyedMapper[Phone] with IdPK {
  def getSingleton = Phone

  object number extends MappedString(this, 20)

  object contact extends MappedLongForeignKey(this, Contact)
}

object Phone extends Phone with LongKeyedMetaMapper[Phone] {
  override def dbTableName = "phones"
}
