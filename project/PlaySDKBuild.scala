import sbt._
import Keys._
import sbt.Keys._
import play.Project._

object PlaySDKBuild extends Build {

  lazy val sdk = play.Project("sphere-sdk").aggregate(spherePlaySDK)

  // ----------------------
  // Play SDK
  // ----------------------

  val sdkVersion = "0.38.0-SNAPSHOT"

  lazy val spherePlaySDK = play.Project(
    "sphere-play-sdk",
    sdkVersion,
    Seq(javaCore),
    path = file("play-sdk")
  ).dependsOn(sphereJavaClient % "compile->compile;test->test").
    // aggregate: clean, compile, publish etc. transitively
    aggregate(sphereJavaClient).
    settings(standardSettings:_*).
    settings(scalaSettings:_*).
    settings(java6Settings:_*).
    settings(testSettings(Libs.scalatest):_*).
    settings(publishSettings:_*)

  // ----------------------
  // Java client
  // ----------------------

  lazy val sphereJavaClient = Project(
    id = "sphere-java-client",
    base = file("java-client"),
    settings =
      Defaults.defaultSettings ++ standardSettings ++ scalaSettings ++ java6Settings ++
      testSettings(Libs.scalatest) ++ publishSettings ++ Seq(
        version := sdkVersion,
        autoScalaLibrary := false, // no dependency on Scala standard library (just for tests)
        crossPaths := false,
        libraryDependencies ++= Seq(
          Libs.asyncHttpClient, Libs.guava, Libs.jodaTime, Libs.jodaConvert,
          Libs.jackson, Libs.jacksonMapper, Libs.jcip,
          Libs.nvI18n        // CountryCode
        )))

  // ----------------------
  // Settings
  // ----------------------

  lazy val standardSettings = Seq[Setting[_]](
    organization := "io.sphere",
    // Don't publish Scaladoc
    publishArtifact in (Compile, packageDoc) := false
  )

  lazy val scalaSettings = Seq[Setting[_]](
    scalaVersion := "2.10.0",
    // Emit warnings for deprecated APIs, emit erasure warnings
    scalacOptions ++= Seq("-deprecation", "-unchecked")
  )

  // Compile the SDK for Java 6, for developers who're still on Java 6
  lazy val java6Settings = Seq[Setting[_]](
    // Emit warnings for deprecated APIs, emit erasure warnings
    javacOptions ++= Seq("-deprecation", "-Xlint:unchecked", "-source", "1.6", "-target", "1.6"),
    // javadoc options
    javacOptions in doc := Seq("-source", "1.6")
  )

  def testSettings(testLibs: ModuleID*) = Seq[Setting[_]](
    parallelExecution in Test := false,
    libraryDependencies ++= Seq(testLibs:_*),
    testOptions in Test <<= (target in Test) map { target => Seq(
      //Tests.Argument(TestFrameworks.ScalaTest, "-l", "disabled integration"),
      Tests.Argument(TestFrameworks.ScalaTest, "-oD"), // show durations
      Tests.Argument(TestFrameworks.ScalaTest, "junitxml(directory=\"%s\")" format (target / "test-reports")))
    }
  )

  // To 'sbt publish' to commercetools public Nexus
  lazy val publishSettings = Seq(
    credentials ++= Seq(
      Credentials(Path.userHome / ".ivy2" / ".ct-credentials"),
      Credentials(Path.userHome / ".ivy2" / ".ct-credentials-public")),
    publishTo <<= (version) { version: String =>
      if(version.trim.endsWith("SNAPSHOT"))
        Some("ct-snapshots" at "http://repo.ci.cloud.commercetools.de/content/repositories/snapshots")
      else
        Some("ct-public-releases" at "http://public-repo.ci.cloud.commercetools.de/content/repositories/releases")
    })

  // ----------------------
  // Dependencies
  // ----------------------

  object Libs {
    lazy val asyncHttpClient = "com.ning" % "async-http-client" % "1.7.16"
    lazy val guava           = "com.google.guava" % "guava" % "12.0"
    lazy val jodaTime        = "joda-time" % "joda-time" % "2.2"
    lazy val jodaConvert     = "org.joda" % "joda-convert" % "1.3.1"
    lazy val jackson         = "org.codehaus.jackson" % "jackson-core-asl" % "1.9.10"
    lazy val jacksonMapper   = "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.10"
    lazy val jcip            = "net.jcip" % "jcip-annotations" % "1.0"
    lazy val nvI18n          = "com.neovisionaries" % "nv-i18n" % "1.4"

    lazy val scalatest       = "org.scalatest" % "scalatest_2.10.0" % "2.0.M5" % "test"
  }
}
