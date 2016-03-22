name := "Lift 2.6 starter template"

version := "0.0.4"

organization := "net.liftweb"

scalaVersion := "2.11.8"

resolvers ++= Seq("snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "https://oss.sonatype.org/content/repositories/releases"
                )

Seq(webSettings :_*)

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

javaOptions ++= Seq(
  "-DOPTIONS=All"
)

env in Compile := Some(file("./src/main/webapp/WEB-INF/jetty-env.xml").asFile)

libraryDependencies ++= {
  val liftVersion = "2.6.2"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile->default" withSources() withJavadoc(),
    "net.liftweb" %% "lift-wizard" % liftVersion % "compile",
    "net.liftweb"       %% "lift-testkit"        % liftVersion % "test",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile",
    "org.postgresql" % "postgresql" % "9.2-1003-jdbc4"  % "compile",
    "org.eclipse.jetty" % "jetty-webapp" % "8.1.7.v20120910"  % "container",
    "org.eclipse.jetty" % "jetty-plus" % "8.1.7.v20120910" % "container",
    "org.eclipse.jetty" % "jetty-jndi" % "8.1.7.v20120910" % "container",
    "net.liftmodules"   %% "lift-jquery-module_2.6" % "2.8",
    "org.eclipse.jetty" % "jetty-webapp"        % "8.1.7.v20120910"  % "container,test",
    "org.eclipse.jetty" % "jetty-plus"          % "8.1.7.v20120910"  % "container,test", // For Jetty Config
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"    % "logback-classic"     % "1.0.6",
    "org.specs2"        %% "specs2"             % "2.3.12"           % "test"
  )
}

libraryDependencies += "com.lihaoyi" %% "upickle" % "0.3.8"


//port in container.Configuration := 8081

