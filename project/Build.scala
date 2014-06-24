import sbt._
import sbt.Configuration
import sbt.Keys._
import com.typesafe.sbteclipse.core.EclipsePlugin
import Release._

import de.johoop.jacoco4sbt._
import de.johoop.jacoco4sbt.JacocoPlugin._
import sbtunidoc.Plugin._
import play.PlayJava
import play.Play.autoImport._
import PlayKeys._
import sbtunidoc.Plugin.UnidocKeys._
import scala.Some

object Build extends Build {

  val writeVersion = taskKey[Unit]("Write the version into a file.")

  val scalaProjectSettings = Seq(autoScalaLibrary := true, crossScalaVersions := Seq("2.10.4", "2.11.0"), crossPaths := true)

  val moduleDependencyGraph = taskKey[Unit]("creates an image which shows the dependencies between the SBT modules")

  val genDoc = taskKey[Seq[File]]("generates the documentation")

  lazy val `jvm-sdk` = (project in file(".")).
    settings(standardSettings:_*).
    settings(
      libraryDependencies += Libs.junitDep
    ).
    settings(unidocSettings:_*).
    settings(docSettings:_*).
    settings(javaUnidocSettings:_*).
    aggregate(`sphere-play-sdk`, common, javaClient, scalaClient, playJavaClient, categories, javaIntegrationTestLib, queries, playJavaTestLib).
    dependsOn(`sphere-play-sdk`, javaIntegrationTestLib).settings(
      crossScalaVersions := Seq("2.10.4", "2.11.0"),
      writeVersion := {
        IO.write(target.value / "version.txt", version.value)
      },
      unidoc in Compile <<= (unidoc in Compile).dependsOn(writeVersion),
      moduleDependencyGraph in Compile := {
        val projectToDependencies = projects.filterNot(_.id.toLowerCase.contains("test")).map { p =>
          val id = p.id
          val deps = p.dependencies.map(_.project).collect { case LocalProject(id) => id} filterNot(_.toLowerCase.contains("test"))
          (id, deps)
        }.toList
        val x = projectToDependencies.map { case (id, deps) =>
          deps.map(dep => '"' + id + '"' + "->" + '"' + dep + '"').mkString("\n")
        }.mkString("\n")
        val content = s"""digraph TrafficLights {
                        |$x
                        |
                        |
                        |overlap=false
                        |label="Module Structure in JVM SDK"
                        |fontsize=12;
                        |}
                        |""".stripMargin
        val graphvizFile = target.value / "deps.txt"
        val imageFile = baseDirectory.value / "documentation-resources" / "architecture" / "deps.png"
        IO.write(graphvizFile, content)
        //requires graphviz http://www.graphviz.org/Download_macos.php
        s"neato -Tpng $graphvizFile" #> imageFile !
      },
      genDoc <<= (baseDirectory, target in unidoc) map { (baseDir, targetDir) =>
        val destination = targetDir / "javaunidoc" / "documentation-resources"
        IO.copyDirectory(baseDir / "documentation-resources", destination)
        IO.listFiles(destination)
      },
      genDoc <<= genDoc.dependsOn(unidoc in Compile)
    ).settings(scalaProjectSettings: _*).settings(scalaSettings:_*)

  lazy val `sphere-play-sdk` = (project in file("play-sdk")).settings(libraryDependencies ++= Seq(javaCore)).
    dependsOn(categories, playJavaClient)
    .settings(standardSettings:_*)
    .settings(playPlugin := true)
    .settings(scalaSettings:_*)
    .settings(javacSettings:_*)
    .settings(genjavadocSettings:_*)
    .settings(docSettings:_*)
    .settings(testSettings(Libs.scalaTest, Libs.playTest, Libs.play):_*)
    .configs(IntegrationTest)
    .settings(
      scalaSource in IntegrationTest <<= baseDirectory (_ / "it"),
      unmanagedResourceDirectories in IntegrationTest <<= baseDirectory (base => Seq(base / "it" / "resources")),
      organization := "io.sphere",
      libraryDependencies += Libs.festAssert % "test"
    ).settings(scalaProjectSettings: _*)

  def jacksonModule(artefactId: String) = "com.fasterxml.jackson.core" % artefactId % "2.3.3"
  def javaProject(name: String) =
    Project(id = name, base = file(name), settings = javaClientSettings ++ jacoco.settings ++ standardSettings).
    configs(IntegrationTest)

  lazy val playJavaClient = Project(
    id = "play-java-client",
    base = file("play-java-client"),
    settings = javaClientSettings
  ).configs(IntegrationTest).dependsOn(scalaClient).settings(javaUnidocSettings:_*).settings(scalaProjectSettings: _*)
    .enablePlugins(PlayJava)

  lazy val scalaClient = Project(
    id = "scala-client",
    base = file("scala-client"),
    settings = javaClientSettings
  ).configs(IntegrationTest).dependsOn(javaClient).settings(javaUnidocSettings:_*).settings(scalaProjectSettings: _*)

  lazy val javaClient = Project(
    id = "java-client",
    base = file("java-client"),
    settings = javaClientSettings
  ).configs(IntegrationTest).dependsOn(common).settings(docSettings: _*)

  lazy val common = javaProject("common").settings(
//sbt buildinfo plugin cannot be used since the generated class requires Scala
    sourceGenerators in Compile <+= (sourceManaged in Compile, version) map { (outDir, v) =>
      val file = outDir / "io" / "sphere" / "sdk" / "meta" / "BuildInfo.java"
      IO.write(file, """
package io.sphere.sdk.meta;

// Don't edit this file - it is autogenerated by sbt
public final class BuildInfo {
    private BuildInfo() {
      //utility class
    }

    public static String version() {
      return """" + v + """";
    }
}
""")
      Seq(file)
    }
  )

  lazy val queries = javaProject("queries").dependsOn(common)

  lazy val categories = javaProject("categories").dependsOn(javaIntegrationTestLib % "it", queries)

  lazy val javaIntegrationTestLib = javaProject("javaIntegrationTestLib").
    dependsOn(javaClient).
    settings(
      autoScalaLibrary := true,
      libraryDependencies += Libs.scalaTestRaw
    ).settings(scalaProjectSettings: _*)

  lazy val playJavaTestLib = javaProject("play-java-test-lib").dependsOn(playJavaClient).
    settings(javaUnidocSettings:_*).settings(scalaProjectSettings: _*).enablePlugins(PlayJava).
    settings(
      libraryDependencies += Libs.festAssert
    )

  lazy val javaClientSettings = Defaults.defaultSettings ++ standardSettings ++ scalaSettings ++ javacSettings ++
    genjavadocSettings ++ docSettings ++
    testSettings(Libs.scalaTest, Libs.logbackClassic, Libs.junitDep) ++ Seq(
    autoScalaLibrary := false, // no dependency on Scala standard library (just for tests)
    crossPaths := false,
    libraryDependencies ++= Seq(
      "com.ning" % "async-http-client" % "1.8.7",
      "com.google.guava" % "guava" % "17.0",
      "com.google.code.findbugs" % "jsr305" % "2.0.3", //optional dependency of guava,
      "joda-time" % "joda-time" % "2.3",
      "org.joda" % "joda-convert" % "1.6",
      jacksonModule("jackson-annotations"),
      jacksonModule("jackson-core"),
      jacksonModule("jackson-databind"),
      "com.fasterxml.jackson.datatype" % "jackson-datatype-guava" % "2.2.0",
      "net.jcip" % "jcip-annotations" % "1.0",
      "com.typesafe" % "config" % "1.2.0",
      "com.neovisionaries" % "nv-i18n" % "1.12",
      "org.apache.commons" % "commons-lang3" % "3.3.2"
    ))

  val Snapshot = "SNAPSHOT"
  def isOnJenkins() = scala.util.Properties.envOrNone("JENKINS_URL").isDefined

  lazy val standardSettings = publishSettings ++ Seq(
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
  ) ++ docSettings

  lazy val docSettings = Seq(
    javacOptions in (Compile, doc) := Seq("-overview", "documentation-resources/javadoc-overview.html", "-notimestamp", "-taglet", "CodeTaglet",
      "-tagletpath", "./project/target/scala-2.10/sbt-0.13/classes",
      "-bottom", """<link rel='stylesheet' href='http://yandex.st/highlightjs/7.4/styles/default.min.css'><script src='http://yandex.st/highlightjs/7.4/highlight.min.js'></script><script>hljs.initHighlightingOnLoad();</script><style>code {font-size: 1.0em;font-family: monospace;}</style>""")
  )

  lazy val scalaSettings = Seq[Setting[_]](
    scalaVersion := "2.10.4",
    // Emit warnings for deprecated APIs, emit erasure warnings
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:implicitConversions", "-language:postfixOps")
  )

  lazy val javacSettings = Seq[Setting[_]](
    javacOptions ++= Seq("-deprecation", "-Xlint:unchecked", "-source", "1.8", "-target", "1.8", "-Xlint:all", "-Xlint:-options", "-Xlint:-path", "-Werror")
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

  object Libs {
    lazy val scalaTestRaw = "org.scalatest" %% "scalatest" % "2.1.3"
    lazy val scalaTest = scalaTestRaw % "test;it"
    lazy val logbackClassic  = "ch.qos.logback" % "logback-classic" % "1.1.2" % "it"
    lazy val junitDep        = "junit" % "junit-dep" % "4.11" % "test"
    lazy val playTest        = "com.typesafe.play" %% "play-test" % javaCore.revision % "it"
    lazy val play            = javaCore % "it"
    lazy val festAssert = "org.easytesting" % "fest-assert" % "1.4"
  }

  override def settings = super.settings ++ Seq(
    //make sure "play eclipse" includes subprojects too
    EclipsePlugin.EclipseKeys.skipParents in ThisBuild := false
  )
}
