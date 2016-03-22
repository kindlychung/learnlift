package bootstrap.liftweb


import code.model._
import code.rest.{Files, Clients, IssuesService}
import code.session.ClientCache
import net.liftmodules.JQueryModule
import net.liftweb._
import net.liftweb.common.{Logger, _}
import net.liftweb.http._
import net.liftweb.http.js.jquery._
import net.liftweb.mapper.{Schemifier, StandardDBVendor, DB}
import net.liftweb.sitemap.Loc._
import net.liftweb.sitemap._
import net.liftweb.util.Helpers._
import net.liftweb.util._

/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */
class Boot extends Logger {
  def boot {

//    def configureDB() {
//      for {
//        driver <- Props.get("db.driver")
//        url <- Props.get("db.url")
//      } yield {
//        val standardVendor =
//          new StandardDBVendor(driver, url, Props.get("db.user"), Props.get("db.password"))
//        LiftRules.unloadHooks.append(standardVendor.closeAllConnections_! _)
//        DB.defineConnectionManager(DefaultConnectionIdentifier, standardVendor)
//      }
//    }

//    configureDB()
//    DefaultConnectionIdentifier.jndiName = "jdbc/dsliftbook"

    Schemifier.schemify(
      true, Schemifier.infoF _,
      Contact
    )

    Schemifier.schemify(
      true, Schemifier.infoF _,
      Contact,
      Phone
    )

    Schemifier.schemify(
      true, Schemifier.infoF _,
      Course,
      Student,
      CourseStudents
    )

    LiftRules.resourceNames = "i18n/resources" :: LiftRules.resourceNames

    // where to search snippet
    LiftRules.addToPackages("code")
    configureMailer()

    IssuesService.init()
    //    Clients.init
//    ClientCache.startClient()
    LiftRules.dispatch.append(Clients)
    LiftRules.dispatch.append(Files)

    val canManage_? = If(
      // always true means everybody can manage
      () => {
        true
      },
      // if false, redirect to root
      () => RedirectResponse("/"))

    val isAdmin_? = If(() => {
      S.param("admin").flatMap(asBoolean).openOr(false)
    }, () => RedirectWithState("/", MessageState(
      "Authorized personnel only" -> NoticeType.Warning))
    )

    // Build SiteMap
    val entries = List(
      Menu.i("menu.home") / "index" >> LocGroup("content"), // the simple way to declare a menu

      //      (Menu("Admin") / "admin" >> LocGroup("admin"))
      (Menu("Admin") / "admin" >> Hidden)
        .submenus(
          Menu(Loc("List", List("list"), "List Contacts", isAdmin_?, LocGroup("admin"))),
          Menu(Loc("Create", List("create"), "Create Contact", isAdmin_?, LocGroup("admin"))),
          Menu(Loc("Edit", List("edit"), "Edit Contact", isAdmin_?, LocGroup("admin"))),
          Menu(Loc("Delete", List("delete"), "Delete Contact", isAdmin_?, LocGroup("admin"))),
          Menu(Loc("View", List("view"), "View Contact", isAdmin_?, LocGroup("admin")))
        ),
      Menu("Search") / "search" >> LocGroup("content"),
      Menu("Contact Us") / "contact" >> LocGroup("footer"),
      Menu("About Us") / "about" >> LocGroup("footer"),
      Menu(Loc("403", "403" :: Nil, "403", Hidden)),
      Menu(Loc("404", "404" :: Nil, "404", Hidden)),
      Menu(Loc("500", "500" :: Nil, "500", Hidden)),
      Menu.i("Finish") / "finish",
      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), S ? "menu.static", LocGroup("content")))
      // Menu(Loc("Static", Link(List("static"), true, "/static/index"), S ? "menu.static", LocGroup("content")))
      // If the url list is the same as the url, e.g. List(a, b) vs "/a/b", then matchHead does not give you any flexibility at all.
      //        Menu(Loc("Static", Link(List("static", "index"), true, "/static/index"), "Static Content", LocGroup("content")))
    )

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries: _*))

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    //Init the jQuery module, see http://liftweb.net/jquery for more information.
    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery = JQueryModule.JQuery191
    JQueryModule.init()

    LiftRules.dispatch.append {
      case Req("error-500" :: Nil, _, _) => {
        () => {
          Full(InternalServerErrorResponse())
        }
      }
      case Req("error-403" :: Nil, _, _) => {
        () => {
          Full(ForbiddenResponse())
        }
      }
    }

    LiftRules.responseTransformers.append {
      case r if r.toResponse.code == 403 =>
        RedirectResponse("/403")
      case r if r.toResponse.code == 404 =>
        RedirectResponse("/404")
      case r if r.toResponse.code == 500 =>
        RedirectResponse("/500")
      case r => r
    }
  }


  def configureMailer() {
    import javax.mail.{Authenticator, PasswordAuthentication}
    System.setProperty("mail.smtp.starttls.enable", "true")
    System.setProperty("mail.smtp.ssl.enable", "true")
    System.setProperty("mail.smtp.host", "smtp.gmail.com")
    System.setProperty("mail.smtp.auth", "true")
    Mailer.authenticator = for {
      user <- Props.get("mail.user")
      pass <- Props.get("mail.password")
    } yield new Authenticator {
      override def getPasswordAuthentication =
        new PasswordAuthentication(user, pass)
    }
  }

}

