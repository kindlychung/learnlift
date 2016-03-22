package code.session

import code.model.Client
import net.liftweb.http.SessionVar

/**
  * User: ggarcia
  * Date: 3/23/13
  * Time: 9:28 AM
  */
object ClientCache extends SessionVar[List[Client]](Nil)