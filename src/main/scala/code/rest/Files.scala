package code.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.http._
import java.io.{File, FileOutputStream}
import net.liftweb.util.Props

/**
  * User: ggarcia
  * Date: 3/23/13
  * Time: 9:25 AM
  */
object Files extends RestHelper {
  serve {
    case "api" :: "files" :: Nil Post req => {
      println(req)
      uploadFile(req)
    }
  }

  def uploadFile(req: Req): LiftResponse = {
    req.uploadedFiles match {
      case FileParamHolder(_, mime, fileName, data) :: Nil =>  {
        val path = Props.get("file.folder", "") + "/" + fileName

        val fos = new FileOutputStream(new File(path))
        fos.write(data)
        fos.close()
      }
      case _ =>
    }
    RedirectResponse("/")
  }
}
