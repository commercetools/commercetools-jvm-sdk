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
  lazy val `jvm-sdk` = (project in file(".")).configs(IntegrationTest)
    .settings(unidocSettings:_*)
    .settings(javaUnidocSettings:_*)
    .settings(unidocProjectFilter in (JavaUnidoc, unidoc) := inAnyProject -- inProjects(`test-lib`))
    .settings(documentationSettings:_*)
    .settings(commonSettings:_*)
    .aggregate(common, `java-client`, `java-client-core`, `java-client-apache-async`, models, `test-lib`)
    .dependsOn(common, `java-client`, `java-client-core`, `java-client-apache-async`, models, `test-lib`)

  lazy val `java-client-core` = project.configs(IntegrationTest).dependsOn(common).settings(commonSettings:_*)

  lazy val `java-client` = project.configs(IntegrationTest).dependsOn(`java-client-core`).settings(commonSettings:_*)
  .settings(libraryDependencies ++= Seq("com.ning" % "async-http-client" % "1.8.7"))

  lazy val `java-client-apache-async` = project.configs(IntegrationTest).dependsOn(`java-client-core`).settings(commonSettings:_*)
  .settings(libraryDependencies ++= Seq("org.apache.httpcomponents" % "httpasyncclient" % "4.0.2"))

  lazy val common = project.configs(IntegrationTest).settings(writeVersionSettings: _*).settings(commonSettings:_*)

  lazy val models = project.configs(IntegrationTest).dependsOn(common, `java-client-apache-async` % "test,it", `test-lib` % "test,it").settings(commonSettings:_*)

  lazy val `test-lib` = project.configs(IntegrationTest).dependsOn(`java-client`, common).settings(commonSettings:_*)
    .settings(
      libraryDependencies ++=
        festAssert ::
        junitDep ::
        junitInterface ::
        Nil
    )


  lazy val junitDep = "junit" % "junit-dep" % "4.11"
  lazy val junitInterface = "com.novocode" % "junit-interface" % "0.11"
  lazy val festAssert = "org.easytesting" % "fest-assert" % "1.4"

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
    val ulMainSphereException: Element = document.select("a[href=\"io/sphere/sdk/exceptions/SphereException.html\"]")
      .parents().get(0).select("ul").get(0)
    val results: List[String] = processUl(ulMainSphereException, "io.sphere.sdk.exceptions.SphereException")

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
        deps.map(dep => '"' + id + '"' + "->" + '"' + dep + '"').filterNot(s => s == "\"models\"->\"java-client-apache-async\"" || s.startsWith("\"test-lib") || s.startsWith("\"jvm-sdk")).mkString("\n")
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
