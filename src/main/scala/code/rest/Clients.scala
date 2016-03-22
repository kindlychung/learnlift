package code.rest

import code.model.Client
import code.session.ClientCache
import net.liftweb.http._
import net.liftweb.http.rest.RestHelper
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.JsonDSL._
import net.liftweb.util.Helpers._
import net.liftweb.http.rest.RestHelper
import net.liftweb.http._
import net.liftweb.json.JsonDSL._
import net.liftweb.util.Helpers._
import code.session.ClientCache
import scala.Some
import scala.xml.Elem
import code.lib.DateUtils._
import java.util.Calendar
import language.implicitConversions


import scala.xml.Elem

/**
  * User: ggarcia
  * Date: 3/23/13
  * Time: 9:25 AM
  */
case class JClient(id: Option[Int], name: String, email: String)

object Clients extends RestHelper {
  serve({
    case "api" :: "clients" :: Nil JsonPost json -> _ => createClient(json)
    case "api" :: "clients" :: AsInt(id) :: Nil JsonPut json -> _ => editClient(id, json)
    case Req("api" :: "clients" :: AsInt(id) :: Nil, _, DeleteRequest) => deleteClient(id)
    case Req("api" :: "clients" :: AsInt(id) :: Nil, _, GetRequest) => getClient(id)
    case Req("api" :: "clients" :: Nil, _, GetRequest) => listClients()
    case Req("api" :: "clients" :: "feeds" :: "rss" :: _, _, GetRequest) => toRss(getClients)
  })

  implicit def elemToByteArray(value: Elem): Array[Byte] = {
    value.toString().getBytes
  }

  implicit def jClient2Client(jClient: JClient): Client = {
    val nextId = ClientCache.is.size + 1

    Client(jClient.id.getOrElse(nextId), jClient.name, jClient.email)
  }


  def toRss(elem: Elem): LiftResponse = {
    InMemoryResponse(
      elem,
      ("Content-Type" -> "application/rss+xml") :: Nil,
      Nil,
      200)
  }

  def getClients: Elem = {
    <rss version="2.0">
      <channel>
        <title>Clients</title>
        <description>List of clients by RSS feed</description>
        <link>http://localhost:8080/api/clients/feeds/rss</link>
        <lastBuildDate>{longDate(Calendar.getInstance().getTimeInMillis)}</lastBuildDate>
        <pubDate> {longDate(Calendar.getInstance().getTimeInMillis)} </pubDate>
        {ClientCache.is.flatMap {
        c =>
          <item>
            <title>Client: {c.id}</title>
            <description>Name: {c.name} - E-mail: {c.email}</description>
            <pubDate> {longDate(Calendar.getInstance().getTimeInMillis)} </pubDate>
          </item>
      }}
      </channel>
    </rss>
  }

  def listClients() = JsonResponse("clients" -> ClientCache.is.map(_.asJson))

  def getClient(id: Int) = {
    getById(id) match {
      case Some(c) => JsonResponse(c.asJson)
      case _ => NotFoundResponse()
    }
  }

  def createClient(json: JValue) = {
    val client: Client = json.extract[JClient]

    ClientCache.set(client :: ClientCache.is)

    JsonResponse(client.asJson)
  }

  def editClient(id: Int, json: JValue) = {
    val client: Client = json.extract[JClient]
    val newList = ClientCache.is.filter(_.id != id)

    ClientCache.set(client :: newList)

    JsonResponse(client.asJson)
  }

  def getById(id: Int) = ClientCache.is.find(_.id == id)

  def deleteClient(id: Int) = {
    getById(id) match {
      case Some(c) => {
        val newList = ClientCache.is.filter(_.id != c.id)

        ClientCache.set(newList)

        JsonResponse(c.asJson)
      }
      case _ => NotFoundResponse()
    }
  }
}
