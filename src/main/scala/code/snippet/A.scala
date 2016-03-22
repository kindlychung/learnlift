package code.snippet

import scala.util.{Failure, Success, Try}
import language.implicitConversions

/**
  * Created by IDEA on 3/14/16.
  */
object A {
  implicit def strTo(s: String): Dclass = {
    val t = Try {
      s.toDouble
    }
    val d = t match {
      case Success(x) => x
      case Failure(e) => 0.0
    }
    new Dclass(d)
  }
}

case class Dclass(d: Double)

object B extends App {
  import A._
  def times2(d: Dclass) = new Dclass(d.d * 2)
  println(times2("2.3"))
}
