import java.io.ByteArrayOutputStream

import de.johoop.jacoco4sbt.JacocoPlugin.{itJacoco, jacoco}
import net.sourceforge.plantuml.{FileFormat, FileFormatOption, SourceStringReader}
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import sbt._
import sbt.Keys._
import sbtunidoc.Plugin.UnidocKeys._
import sbtunidoc.Plugin._

import scala.language.postfixOps
import scala.collection.JavaConversions._

object Build extends Build {

  lazy val commonSettings: Seq[sbt.Def.Setting[_]] =
    Defaults.itSettings ++ jacoco.settings ++ itJacoco.settings ++ Release.publishSettings ++ Seq(
      parallelExecution in IntegrationTest := false,
      parallelExecution in itJacoco.Config:= false,
      testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
    )

  //the project definition have to be in .scala files for the module dependency graph
  lazy val `sphere-jvm-sdk` = (project in file(".")).configs(IntegrationTest)
    .settings(unidocSettings:_*)
    .settings(javaUnidocSettings:_*)
    .settings(unidocProjectFilter in (JavaUnidoc, unidoc) := inAnyProject -- inProjects(`sphere-test-lib`, `sphere-java-client-ning-1_8`))//need to exclude duplicated classes or "javadoc: error - com.sun.tools.doclets.internal.toolkit.util.DocletAbortException: java.lang.NullPointerException" appears
    .settings(documentationSettings:_*)
    .settings(commonSettings:_*)
    .aggregate(`sphere-common`, `sphere-java-client`, `sphere-java-client-core`, `sphere-java-client-apache-async`, `sphere-models`, `sphere-test-lib`, `sphere-java-client-ning-1_8`, `sphere-java-client-ning-1_9`)
    .dependsOn(`sphere-common`, `sphere-java-client`, `sphere-java-client-core`, `sphere-java-client-apache-async`, `sphere-models`, `sphere-test-lib`)

  lazy val `sphere-java-client-core` = project.configs(IntegrationTest).dependsOn(`sphere-common`).settings(commonSettings:_*)
    .settings(libraryDependencies += junitDep)

  lazy val `sphere-java-client-internal-test` = project.dependsOn(`sphere-java-client-core`).settings(commonSettings:_*).settings(
    libraryDependencies ++=
        junitDep ::
        junitInterface ::
        assertj ::
        Nil
  ).configs(IntegrationTest)

  lazy val `sphere-java-client` = project.configs(IntegrationTest).dependsOn(`sphere-java-client-ning-1_9`).settings(commonSettings:_*)

  lazy val `sphere-java-client-ning-1_8` = project.configs(IntegrationTest).dependsOn(`sphere-java-client-core`, `sphere-java-client-internal-test` % "test,it").settings(commonSettings:_*)
    .settings(libraryDependencies += "com.ning" % "async-http-client" % "1.8.7").configs(IntegrationTest)

  lazy val `sphere-java-client-ning-1_9` = project.configs(IntegrationTest).dependsOn(`sphere-java-client-core`, `sphere-java-client-internal-test` % "test,it").settings(commonSettings:_*)
    .settings(libraryDependencies += "com.ning" % "async-http-client" % "1.9.18").configs(IntegrationTest)

  lazy val `sphere-java-client-apache-async` = project.configs(IntegrationTest).dependsOn(`sphere-java-client-core`, `sphere-java-client-internal-test` % "test,it").settings(commonSettings:_*)
    .settings(libraryDependencies += "org.apache.httpcomponents" % "httpasyncclient" % "4.0.2").configs(IntegrationTest)

  lazy val `sphere-common` = project.configs(IntegrationTest).settings(writeVersionSettings: _*).settings(commonSettings:_*)

  lazy val `sphere-models` = project.configs(IntegrationTest).dependsOn(`sphere-common`, `sphere-java-client-apache-async` % "test,it", `sphere-test-lib` % "test,it").settings(commonSettings:_*)

  lazy val `sphere-test-lib` = project.configs(IntegrationTest).dependsOn(`sphere-java-client`, `sphere-common`).settings(commonSettings:_*)
    .settings(
      libraryDependencies ++=
        junitDep ::
        junitInterface ::
        assertj ::
        Nil
    )


  lazy val junitDep = "junit" % "junit-dep" % "4.11"
  lazy val assertj =  "org.assertj" % "assertj-core" % "3.0.0"
  lazy val junitInterface = "com.novocode" % "junit-interface" % "0.11"

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
    os.close
    val outFile = javaUnidocDir / "documentation-resources" / "images" / "uml" / "exception-hierarchy.svg"
    IO.write(outFile, os.toByteArray())
  }

  val documentationSettings = Seq(
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
        deps.map(dep => '"' + id + '"' + "->" + '"' + dep + '"').filterNot(s => s == "\"models\"->\"java-client-apache-async\"" || s.startsWith('"' + `sphere-java-client-internal-test`.id) || s.startsWith("\"test-lib") || s.startsWith("\"jvm-sdk")).mkString("\n")
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

    public static String version() {
      return """" + v + """";
    }
}
""")
      Seq(file)
    }
  )
}
