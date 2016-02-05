import java.io.ByteArrayOutputStream
import java.lang.reflect.Method

import Libs._
import net.sourceforge.plantuml.{FileFormat, FileFormatOption, SourceStringReader}
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import sbt.Keys._
import sbt._
import sbtunidoc.Plugin.UnidocKeys._
import sbtunidoc.Plugin._

import scala.collection.JavaConversions._
import scala.language.postfixOps
import scala.util.Try

object Build extends Build {

  lazy val commonSettings: Seq[sbt.Def.Setting[_]] =
    Defaults.itSettings ++ Release.publishSettings ++ Seq(
      parallelExecution in IntegrationTest := false,
      testOptions += Tests.Argument(TestFrameworks.JUnit, "-v"),
      libraryDependencies += `logback-classic` % "test,it",
      testOptions in IntegrationTest += Tests.Setup(loader => Try {
        val clazz = loader.loadClass("io.sphere.sdk.test.IntegrationTest")
        val setupClient = clazz.getDeclaredMethod("setupClient")
        setupClient.invoke(null)
      } ),
      testOptions in IntegrationTest += Tests.Cleanup(loader => Try {
        val clazz = loader.loadClass("io.sphere.sdk.test.IntegrationTest")
        val close = clazz.getDeclaredMethod("shutdownClient")
        close.invoke(null)
      } )
    )

  //the project definition have to be in .scala files for the module dependency graph
  lazy val `sphere-jvm-sdk` = (project in file(".")).configs(IntegrationTest)
    .settings(unidocSettings:_*)
    .settings(javaUnidocSettings:_*)
    .settings(unidocProjectFilter in (JavaUnidoc, unidoc) := inAnyProject -- inProjects(`sphere-test-lib`, `sphere-java-client-ahc-1_8`, `sphere-java-client-ahc-1_9`, `sphere-java-client-apache-async`, `sphere-java-client-internal-test`))//need to exclude duplicated classes or "javadoc: error - com.sun.tools.doclets.internal.toolkit.util.DocletAbortException: java.lang.NullPointerException" appears
    .settings(documentationSettings:_*)
    .settings(commonSettings:_*)
    .aggregate(`sdk-http-ahc-1_8`, `sdk-http-ahc-1_9`, `sdk-http-ahc-2_0`, `sdk-http-apache-async`, `sdk-http`, `sphere-common`, `sphere-convenience`, `sphere-java-client`, `sphere-java-client-core`, `sphere-java-client-apache-async`, `sphere-models`, `sphere-test-lib`, `sphere-java-client-ahc-1_8`, `sphere-java-client-ahc-1_9`, `sphere-java-client-ahc-2_0`)
    .dependsOn(`sdk-http-ahc-2_0`, `sdk-http-apache-async`, `sdk-http`, `sphere-common`, `sphere-convenience`, `sphere-java-client`, `sphere-java-client-core`, `sphere-java-client-apache-async`, `sphere-models`, `sphere-test-lib`, `sphere-java-client-ahc-2_0` % "test")
    .settings(libraryDependencies += "net.sourceforge.plantuml" % "plantuml" % "8019" % "test")

  lazy val `sphere-java-client-core` = project.configs(IntegrationTest).dependsOn(`sphere-common`).settings(commonSettings:_*)
    .settings(libraryDependencies ++= allTestLibs.map(_ % "test,it"))

  lazy val `sphere-java-client-internal-test` = project.dependsOn(`sphere-java-client-core`).settings(commonSettings:_*).settings(
    libraryDependencies ++= allTestLibs,
    libraryDependencies += `commons-io`
  ).configs(IntegrationTest)

  lazy val `sphere-java-client` = project.configs(IntegrationTest).dependsOn(`sphere-java-client-ahc-2_0`).settings(commonSettings:_*)

  lazy val `sphere-java-client-ahc-1_8` = project.configs(IntegrationTest).dependsOn(`sdk-http-ahc-1_8`, `sphere-java-client-core`, `sphere-java-client-internal-test` % "test,it", `sphere-models` % "test,it", `sphere-test-lib` % "test,it").settings(commonSettings:_*)
    .settings(libraryDependencies += Libs.`async-http-client-1.8` % "test,it" force()).configs(IntegrationTest)

  lazy val `sphere-java-client-ahc-1_9` = project.configs(IntegrationTest).dependsOn(`sdk-http-ahc-1_9`, `sphere-java-client-core`, `sphere-java-client-internal-test` % "test,it").settings(commonSettings:_*)
    .settings().configs(IntegrationTest)

  lazy val `sphere-java-client-ahc-2_0` = project.configs(IntegrationTest).dependsOn(`sdk-http-ahc-2_0`, `sphere-java-client-core`, `sphere-java-client-internal-test` % "test,it").settings(commonSettings:_*)
    .settings().configs(IntegrationTest)

  lazy val `sphere-java-client-apache-async` = project.configs(IntegrationTest).dependsOn(`sdk-http-apache-async`, `sphere-java-client-core`, `sphere-java-client-internal-test` % "test,it").settings(commonSettings:_*)
    .settings().configs(IntegrationTest)

  lazy val `sdk-http-ahc-1_8` = project.configs(IntegrationTest).dependsOn(`sdk-http`, `sphere-java-client-internal-test` % "test,it").settings(commonSettings:_*)
    .settings(libraryDependencies ++= Seq(`async-http-client-1.8`, `commons-io`)).configs(IntegrationTest)

  lazy val `sdk-http-ahc-1_9` = project.configs(IntegrationTest).dependsOn(`sdk-http`, `sphere-java-client-internal-test` % "test,it").settings(commonSettings:_*)
    .settings(libraryDependencies += `async-http-client-1.9`).configs(IntegrationTest)

  lazy val `sdk-http-ahc-2_0` = project.configs(IntegrationTest).dependsOn(`sdk-http`, `sphere-java-client-internal-test` % "test,it").settings(commonSettings:_*)
    .settings(libraryDependencies += `async-http-client-2.0`).configs(IntegrationTest)

  lazy val `sdk-http-apache-async` = project.configs(IntegrationTest).dependsOn(`sdk-http`, `sphere-java-client-internal-test` % "test,it").settings(commonSettings:_*)
    .settings(libraryDependencies ++= `apache-httpasyncclient` :: `commons-io` :: Nil).configs(IntegrationTest)

  lazy val `sphere-common` = project.configs(IntegrationTest).dependsOn(`sdk-http`)
    .settings(writeVersionSettings: _*)
    .settings(commonSettings:_*)
    .settings(libraryDependencies ++= `jackson` ++ allTestLibs.map(_ % "test,it") ++
    (`moneta` ::
    `commons-lang3` ::
    `slf4j-api` ::
    `nv-i18n` ::
    `jsr305` ::
     Nil)
    )

  lazy val `sdk-http` = project.configs(IntegrationTest)
    .settings(commonSettings:_*)
    .settings(libraryDependencies ++= allTestLibs.map(_ % "test,it") ++
    (
    `slf4j-api` ::
    `commons-lang3` ::
    `jsr305` ::
     Nil)
    )

  lazy val `sphere-models` = project.configs(IntegrationTest)
    .dependsOn(`sphere-common`, `sphere-java-client-apache-async` % "test,it", `sphere-test-lib` % "test,it")
    .settings(commonSettings:_*)
    .settings(libraryDependencies += `gson` % "test,it")

  lazy val `sphere-convenience` = project.configs(IntegrationTest)
    .dependsOn(`sphere-common`, `sphere-java-client-core`, `sphere-test-lib` % "test,it", `sphere-models` % "test,it")
    .settings(commonSettings:_*)
    .settings(libraryDependencies += `reactive-streams`)

  lazy val `sphere-test-lib` = project.configs(IntegrationTest).dependsOn(`sphere-java-client`, `sphere-common`).settings(commonSettings:_*)
    .settings(
      libraryDependencies ++= allTestLibs ++ (`commons-io` :: Nil)
    )

  val genDoc = taskKey[Seq[File]]("generates the documentation")

  val moduleDependencyGraph = taskKey[Unit]("creates an image which shows the dependencies between the SBT modules")
  val writeVersion = taskKey[Unit]("Write the version into a file.")

  def plantUml(javaUnidocDir: File): Unit = {

    def processLi(element: Element, parentClass: String): List[String] = {
      val elementWithLink: Element = element.children().find(child => child.tagName() == "a").get
      val clazz = elementWithLink.attr("href").replace(".html", "").replace("/", ".")
      val subClassesUl = element.children().find(e => e.tagName() == "ul")
      val children = subClassesUl.map(e => processUl(e, clazz)).getOrElse(Nil).toList
      List(s"$parentClass <|-- $clazz") ++ children
    }

    def processUl(element: Element, parentClass: String): List[String] = {
      val subExceptions = element.children()
      subExceptions.flatMap(e => processLi(e, parentClass)).toList
    }

    val classHierarchyHtml = IO.read(javaUnidocDir / "overview-tree.html")
    val document = Jsoup.parse(classHierarchyHtml)
    val ulMainSphereException: Element = document.select("a[href=\"io/sphere/sdk/models/SphereException.html\"]")
      .parents().get(0).select("ul").get(0)
    val results: List[String] = processUl(ulMainSphereException, "io.sphere.sdk.models.SphereException")

    val source = "@startuml\n" + "" + results.mkString("\n") + "\n@enduml"

    val reader = new SourceStringReader(source)
    val os = new ByteArrayOutputStream()
    val desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG))
    os.close()
    val outFile = javaUnidocDir / "documentation-resources" / "images" / "uml" / "exception-hierarchy.svg"
    IO.write(outFile, os.toByteArray())
  }

  val documentationSettings: Seq[Def.Setting[_]] = Seq(
    writeVersion := {
      IO.write(target.value / "version.txt", version.value)
    },
    moduleDependencyGraph in Compile := {
      val projectToDependencies = projects.map { p =>
        val id = p.id
        val deps = p.dependencies.map(_.project).collect { case LocalProject(id) => id} filterNot(_.toLowerCase.contains("test"))
        (id, deps)
      }.toList
      val x = projectToDependencies.map { case (id, deps) =>
        deps.map(dep => '"' + id + '"' + "->" + '"' + dep + '"').filterNot(s =>
          s == "\"" + `sphere-models`.id +  "\"->\"" + `sphere-java-client-apache-async`.id + "\""
            || s.startsWith('"' + `sphere-java-client-internal-test`.id)
            || s.startsWith("\"" + `sphere-test-lib`.id)
            || s.startsWith("\"" + `sphere-jvm-sdk`.id)).mkString("\n")
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
    unidoc in Compile <<= (unidoc in Compile).dependsOn(writeVersion),
    genDoc <<= (baseDirectory, target in unidoc) map { (baseDir, targetDir) =>
      val destination = targetDir / "javaunidoc" / "documentation-resources"
      IO.copyDirectory(baseDir / "documentation-resources", destination)
      plantUml(targetDir / "javaunidoc")
      IO.listFiles(destination)
    },
    genDoc <<= genDoc.dependsOn(unidoc in Compile)
  )

  val writeVersionSettings = Seq(
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

    public static String userAgent() {
         return "SPHERE.IO JVM SDK " + version();
    }

    public static String version() {
      return """" + v + """";
    }
}
""")
      Seq(file)
    }
  )
}
