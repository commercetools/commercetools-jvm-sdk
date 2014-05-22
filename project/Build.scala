import sbt._
import sbt.Configuration
import sbt.Keys._
import play.Project._
import com.typesafe.sbteclipse.core.EclipsePlugin
import Release._

import de.johoop.jacoco4sbt._
import JacocoPlugin._
import sbtunidoc.Plugin._

object Build extends Build {

  lazy val sdk = play.Project("sphere-sdk").
    settings(standardSettings:_*).
    settings(
      libraryDependencies += Libs.junitDep
    ).
    settings(unidocSettings:_*).
    settings(docSettings:_*).
    settings(javaUnidocSettings:_*).
    aggregate(spherePlaySDK)

  lazy val spherePlaySDK = play.Project(
    "sphere-play-sdk",
    dependencies = Seq(javaCore),
    path = file("play-sdk")
  ).dependsOn(javaClient % "compile->compile;test->test;it->it")
    // aggregate: clean, compile, publish etc. transitively
    .aggregate(javaClient, common, categories)
    .settings(standardSettings:_*)
    .settings(playPlugin := true)
    .settings(scalaSettings:_*)
    .settings(java6Settings:_*)
    .settings(genjavadocSettings:_*)
    .settings(docSettings:_*)
    .settings(testSettings(Libs.scalaTest, Libs.playTest, Libs.play):_*)
    .configs(IntegrationTest)
    .settings(
      scalaSource in IntegrationTest <<= baseDirectory (_ / "it"),
      unmanagedResourceDirectories in IntegrationTest <<= baseDirectory (base => Seq(base / "it" / "resources"))
    )

  def jacksonModule(artefactId: String) = "com.fasterxml.jackson.core" % artefactId % "2.3.3"
  def javaProject(name: String) =
    Project(id = name, base = file(name), settings = javaClientSettings ++ jacoco.settings).
    configs(IntegrationTest)

  lazy val javaClient = Project(
    id = "java-client",
    base = file("java-client"),
    settings = javaClientSettings
  ).configs(IntegrationTest).dependsOn(common)

  lazy val common = javaProject("common")

  lazy val categories = javaProject("categories").dependsOn(common, javaIntegrationTestLib % "it")

  lazy val javaIntegrationTestLib = javaProject("javaIntegrationTestLib").
    dependsOn(javaClient).
    settings(
      autoScalaLibrary := true,
      libraryDependencies += Libs.scalaTestRaw
    )

  lazy val javaClientSettings = Defaults.defaultSettings ++ standardSettings ++ scalaSettings ++ java6Settings ++
    osgiSettings(clientBundleExports, clientBundlePrivate) ++ genjavadocSettings ++ docSettings ++
    testSettings(Libs.scalaTest, Libs.logbackClassic, Libs.junitDep) ++ Seq(
    autoScalaLibrary := false, // no dependency on Scala standard library (just for tests)
    crossPaths := false,
    libraryDependencies ++= Seq(
      "com.ning" % "async-http-client" % "1.8.7",
      "com.google.guava" % "guava" % "17.0",
      "com.google.code.findbugs" % "jsr305" % "2.0.3", //needed by guava,
      "joda-time" % "joda-time" % "2.3",
      "org.joda" % "joda-convert" % "1.6",
      jacksonModule("jackson-annotations"),
      jacksonModule("jackson-core"),
      jacksonModule("jackson-databind"),
      "com.fasterxml.jackson.datatype" % "jackson-datatype-guava" % "2.2.0",
      "net.jcip" % "jcip-annotations" % "1.0",
      "com.typesafe" % "config" % "1.2.0",
      "com.neovisionaries" % "nv-i18n" % "1.12"
    ))

  val Snapshot = "SNAPSHOT"
  def isOnJenkins() = scala.util.Properties.envOrNone("JENKINS_URL").isDefined

  lazy val standardSettings = publishSettings ++ Seq(
    organization := "io.sphere",
    version <<= version in ThisBuild,
    licenses := Seq("Apache" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    homepage := Some(url("https://github.com/commercetools/sphere-play-sdk")),
    version in ThisBuild <<= (version in ThisBuild) { v =>
      //Jenkins is supposed to publish every snapshot artifact which can be distinguished per Git commit.
      if (isOnJenkins) {
        val shortenedGitHash = Process("git rev-parse HEAD").lines.head.substring(0, 7)
        if(v.endsWith(Snapshot) && !v.contains(shortenedGitHash)){
          v.replace(Snapshot, "") + shortenedGitHash + "-" + Snapshot
        }else {
          v
        }
      } else {
        v
      }
    }
  )

  lazy val docSettings = Seq(
    javacOptions in (Compile, doc) := Seq("-notimestamp", "-taglet", "CodeTaglet",
      "-tagletpath", "./project/target/scala-2.10/sbt-0.13/classes",
      "-bottom", """<link rel='stylesheet' href='http://yandex.st/highlightjs/7.4/styles/default.min.css'><script src='http://yandex.st/highlightjs/7.4/highlight.min.js'></script><script>hljs.initHighlightingOnLoad();</script><style>code {font-size: 1.0em;font-family: monospace;}</style>""")
  )

  lazy val scalaSettings = Seq[Setting[_]](
    scalaVersion := "2.10.4",
    // Emit warnings for deprecated APIs, emit erasure warnings
    scalacOptions ++= Seq("-deprecation", "-unchecked")
  )

  // Compile the SDK for Java 6, for developers who're still on Java 6
  lazy val java6Settings = Seq[Setting[_]](
    // Emit warnings for deprecated APIs, emit erasure warnings
    javacOptions ++= Seq("-deprecation", "-Xlint:unchecked", "-source", "1.6", "-target", "1.6", "-Xlint:-options"),
    // javadoc options
    javacOptions in doc := Seq("-source", "1.6")
  )

  def testSettings(testLibs: ModuleID*): Seq[Setting[_]] = {
     Defaults.itSettings ++ jacoco.settings ++ itJacoco.settings ++ Seq(Test, jacoco.Config, IntegrationTest, itJacoco.Config).map { testScope: Configuration =>
       Seq[Setting[_]](
         parallelExecution in testScope := false,
         libraryDependencies ++= Seq(testLibs:_*),
         testOptions in testScope <<= (target in testScope) map { targetDir => Seq(
           //Tests.Argument(TestFrameworks.ScalaTest, "-l", "disabled integration"),
           Tests.Argument(TestFrameworks.ScalaTest, "-oD"), // show durations
           Tests.Argument(TestFrameworks.ScalaTest, "-u", (targetDir / "test-reports").getCanonicalPath))
         }
       )
     }.flatten
  }

  def osgiSettings(exports: Seq[String], privatePackages: Seq[String]) = {
    import com.typesafe.sbt.osgi.{SbtOsgi, OsgiKeys}
    import OsgiKeys._

    SbtOsgi.osgiSettings ++ Seq(
      exportPackage := exports,
      privatePackage := privatePackages
    )
  }

  lazy val clientBundleExports = Seq(
    "io.sphere.client.*"
  )

  lazy val clientBundlePrivate = Seq(
    "io.sphere.internal.*"
  )

  object Libs {
    lazy val scalaTestRaw = "org.scalatest" %% "scalatest" % "2.1.3"
    lazy val scalaTest = scalaTestRaw % "test;it"
    lazy val logbackClassic  = "ch.qos.logback" % "logback-classic" % "1.1.2" % "it"
    lazy val junitDep        = "junit" % "junit-dep" % "4.11" % "test"
    lazy val playTest        = "com.typesafe.play" %% "play-test" % javaCore.revision % "it"
    lazy val play            = javaCore % "it"
  }

  override def settings = super.settings ++ Seq(
    //make sure "play eclipse" includes subprojects too
    EclipsePlugin.EclipseKeys.skipParents in ThisBuild := false
  )
}
