ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.1"

lazy val root = (project in file("."))
  .settings(
    name := "OpenApiScaffold",
    idePackagePrefix := Some("com.jfast")
  )


libraryDependencies += "com.github.jknack" % "handlebars.java" % "4.4.0"
libraryDependencies += "io.swagger.parser.v3" % "swagger-parser" % "2.1.21"
libraryDependencies += "info.picocli" % "picocli" % "4.7.6"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "2.0.13" % "test"
libraryDependencies += "com.google.code.gson" % "gson" % "2.10.1"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.18"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.18" % "test"