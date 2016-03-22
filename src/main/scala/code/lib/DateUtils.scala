package code.lib

import java.text.SimpleDateFormat
import java.util.Date

/**
  * User: ggarcia
  * Date: 4/8/13
  * Time: 10:26 AM
  */
object DateUtils {
  def formatDate(format: String)(date: Long): String = {
    val sdf: SimpleDateFormat = new SimpleDateFormat(format)
    sdf.format(new Date(date))
  }

  def longDate(date: Long): String = formatDate("yyyy-MM-dd'T'HH:mm:ssZ")(date)
}
