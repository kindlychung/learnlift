package code.snippet

/**
  * Created by IDEA on 3/15/16.
  */

import net.liftweb.http.SHtml
import net.liftweb.json.JValue
import net.liftweb.util.BindHelpers._
import net.liftweb.http.js.JsCmds.{JsCrVar, Script}
import net.liftweb.http.js.JE.{JsFunc, JsRaw}
import net.liftweb.http.js.JsCmds
import scala.util.Random
import xml.Text
import net.liftweb.util.BindHelpers._
import net.liftweb.http.js.JsCmds
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JE.JsRaw
import net.liftweb.http.SHtml
import net.liftweb.json._


object JsCommand {
  def jsCommand = {
    println("invoking js from server")
    val command1 = JsCrVar("fromServer",
      JsRaw("""$("#cmd1").html("this string was sent from the server")"""))
//    val command2 = JsFunc("myFunction").cmd
    val command2 = JsRaw(
      """
        |myFunction()
      """.stripMargin).cmd
//    val command3 = JsCmds.SetHtml("cmd3", Text("changing element content using Lift 's JsCmds"))
    val command3 = JsRaw(
      """
        |$("#cmd3").html("changing element content from lift.")
      """.stripMargin).cmd
    val command4 = JsRaw(
      """for(i = 0; i < 10; i++) {
        |$("#cmd4").append("<button data-number='" + i + "'>" + i + "</button>")
        |}
        |$("#cmd4 button").click(function() {
        |confirm("Do you want to delete number: " + $(this).data("number"))
        |})""".stripMargin
    ).cmd
    val s = "*" #> Script(command1 & command2 & command3 & command4)
    s
  }

  implicit val formats = net.liftweb.json.DefaultFormats

  var persons = "Carly" :: "Joe" :: "John" :: "Mary" :: Nil

  def loadData = {
    def funcBody(json: JValue) = {
      val p = json.extract[String]

      persons = persons.filterNot(_ == p)

      JsRaw(
        """
          $("#person [value='""" + p + """']").remove()
                                       """).cmd &
        JsCmds.Alert(p + " was removed")
    }

    Function("ajaxDeletePeople", List("person"), SHtml.jsonCall(JsRaw("person"), (value: JValue) => funcBody(value)))
  }


  def jsFunction = {

//    "*" #> SHtml.ajaxInvoke(() => JsCmds.Alert("responding to the ajax call"))
    "*" #> Script(loadData)
  }

  val r = scala.util.Random

  // Infinite loop between client and server
  def loadData1 = {
    def funcBody(json: JValue) = {
      val p = json.extract[String]
      val i = r.nextInt(4)
      JsRaw(
        """$("#person").val('""" + persons(i) + """');""" +
        """
          |ajaxRandomPerson($("#person").val())
        """.stripMargin
      ).cmd
    }
    Function("ajaxRandomPerson", List("person"), SHtml.jsonCall(JsRaw("person"), (value: JValue) => funcBody(value)))
  }

  def jsFunction1 = {
    "*" #> Script(loadData1)
  }
}
