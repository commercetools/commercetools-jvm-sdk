import Build._

organization in ThisBuild := "io.sphere.sdk.jvm"

autoScalaLibrary in ThisBuild := false // no dependency to Scala standard library

crossPaths in ThisBuild := false

Release.publishSettings

licenses in ThisBuild := Seq("Apache" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

homepage in ThisBuild := Some(url("https://github.com/sphereio/sphere-jvm-sdk"))

javacOptions in (Compile, doc) in ThisBuild := Seq("-overview", "documentation-resources/javadoc-overview.html", "-notimestamp", "-taglet", "CodeTaglet", "-taglet", "DocumentationTaglet",
  "-tagletpath", "./project/target/scala-2.10/sbt-0.13/classes",
  "-bottom", """<link rel='stylesheet' href='http://yandex.st/highlightjs/7.4/styles/default.min.css'><script src='http://yandex.st/highlightjs/7.4/highlight.min.js'></script><script>hljs.initHighlightingOnLoad();</script><style>code {font-size: 1.0em;font-family: monospace;}</style><script src='https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js'></script>""" +
    """\n<span id='custom-javascripts'></span>\n<script>var pathPrefix = $(\".navList a[href$='index-all.html']\").attr(\"href\").replace(\"index-all.html\", \"\"); var closingScriptTag = \"</\" + \"script>\"; \n$('#custom-javascripts').append(\"<script src='\" + pathPrefix + \"documentation-resources/javascripts/main.js'>\" + closingScriptTag + \"<link rel='stylesheet' href='\" + pathPrefix + \"documentation-resources/stylesheets/main.css'>\");</script>""",
  "-encoding", "UTF-8", "-charset", "UTF-8", "-docencoding", "UTF-8")

javacOptions in ThisBuild ++= Seq("-deprecation", "-Xlint:unchecked", "-source", "1.8", "-target", "1.8", "-Xlint:all", "-Xlint:-options", "-Xlint:-path", "-Werror", "-parameters")

genjavadocSettings

releaseSettings

Release.publishSettings

net.virtualvoid.sbt.graph.Plugin.graphSettings