import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  lazy val main = PlayProject("sphere-applications") dependsOn(sampleStore) aggregate (sampleStore)

  lazy val sampleStore = PlayProject(
    "sample-store", "1.0-SNAPSHOT",
    path = file("sample-store-java"),
    mainLang = JAVA
  ).dependsOn(sdk).aggregate(sdk)

  lazy val sdk = PlayProject(
    "sphere-sdk", "1.0-SNAPSHOT", Seq(), path = file("sphere-sdk-java")
  ).settings(
    organization := "de.commercetools",
    scalaVersion := "2.9.1",
    libraryDependencies ++= Seq(Libs.commonsCodec, Libs.commonsIO)
  )
}

object Libs {
  lazy val commonsCodec = "commons-codec" % "commons-codec" % "1.5"
  lazy val commonsIO = "commons-io" % "commons-io" % "2.3"
}