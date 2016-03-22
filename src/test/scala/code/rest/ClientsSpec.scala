package code.rest

import code.model.Client
import code.session.ClientCache
import net.liftweb.http.JsonResponse
import net.liftweb.json.JsonDSL._
import net.liftweb.mockweb.MockWeb._
import org.specs2.mutable.Specification

/**
  * User: ggarcia
  * Date: 4/7/13
  * Time: 4:48 PM
  */
class ClientsSpec extends Specification {
  val clients = Client(1, "First", "first@test.com") ::
    Client(2, "Second", "first@test.com") :: Nil

  "Client REST API" should {
    "list clients" in {
      testS("/api/clients") {
        ClientCache.set(clients)

        val expectedResp = JsonResponse("clients" -> clients.map(_.asJson))

        Clients.listClients() ==== expectedResp
      }
    }

    "fetch details from a given client" in {
      testS("/api/clients/1") {
        ClientCache.set(clients)

        val expectedResp = JsonResponse(
          clients.head.asJson,
          Nil, Nil,
          200
        )
        Clients.getClient(1) ==== expectedResp
      }
    }

    "be able to create new clients" in {
      testS("/api/clients") {
        val expectedResp = JsonResponse(
          clients.head.asJson,
          Nil, Nil,
          200
        )

        Clients.createClient(clients.head.asJson)
        Clients.getClient(1) ==== expectedResp
        ClientCache.is.size === 1
      }
    }
  }
}
