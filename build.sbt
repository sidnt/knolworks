import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.knoldus",
      scalaVersion := "2.13.4",
      version      := "0.0.1"
    )),
    name := "knolworks",
    libraryDependencies += scalaTest % Test
  )
