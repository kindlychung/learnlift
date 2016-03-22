package code.snippet

import net.liftweb.util.BindHelpers._
import xml.Text

/**
  * Created by IDEA on 3/14/16.
  */
object Animals {
  def list = {
    val animals = List(
      ("Dog", "(Canis lupus familiaris)"),
      ("Cat", "(Felis catus)"),
      ("Giraffe", "(Giraffa camelopardalis)"),
      ("Lion", "(Panthera leo)"),
      ("Horse", "(Equus ferus caballus)")
    )
    "li *" #> animals.map {
      a =>
        ".name *" #> Text(a._1) &
          ".sname *" #> Text(a._2)
    }
  }
}



