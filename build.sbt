import Build.Libs._

resolvers in ThisBuild += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

Release.publishSettings

organization in ThisBuild := "io.sphere.sdk.jvm"

libraryDependencies in ThisBuild ++= Seq(
  festAssert % "test",
  junitDep,
  junitInterface % "test"
)

autoScalaLibrary := false // no dependency on Scala standard library