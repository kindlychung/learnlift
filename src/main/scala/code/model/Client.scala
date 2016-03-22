package code.model
import net.liftweb.json.JsonDSL._

/**
 * Created by IDEA on 29/10/15.
 */
case class Client(id: Int, name: String, email: String) {
  def asJson = {
    ("id" -> id) ~
      ("name" -> name) ~
      ("email" -> email)
  }
}
