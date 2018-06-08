
lazy val root = (project in file("."))
  .settings(
    organization := "uk.me.mcnulty",
    name := "lets-build-a-compiler",
    version := "0.1.0",
    scalaVersion := "2.12.6",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % "test"
    )
  )
