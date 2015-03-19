import Build._

organization in ThisBuild := "io.sphere.sdk.jvm"

libraryDependencies in ThisBuild ++=
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.4.3" ::
  "com.fasterxml.jackson.core" % "jackson-core" % "2.4.3" ::
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.3" ::
  "com.fasterxml.jackson.module" % "jackson-module-parameter-names" % "2.4.3" ::
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "2.4.3" ::
  "org.zapodot" % "jackson-databind-java-optional" % "2.4.3" ::
  "com.neovisionaries" % "nv-i18n" % "1.14" ::
  "org.apache.commons" % "commons-lang3" % "3.3.2" ::
  "org.apache.commons" % "commons-io" % "1.3.2" ::
  "com.github.slugify" % "slugify" % "2.1.2" ::
  "org.javamoney" % "moneta" % "1.0-RC2" ::
  "org.slf4j" % "slf4j-api" % "1.7.7" ::
  festAssert % "test,it" ::
  junitDep % "test,it" ::
  junitInterface % "test,it" ::
  "ch.qos.logback" % "logback-classic" % "1.1.2" % "it" ::
  "org.slf4j" % "jul-to-slf4j" % "1.7.7" % "it" ::
  "com.google.code.gson" % "gson" % "2.3.1" % "it,test" ::
  Nil

autoScalaLibrary in ThisBuild := false // no dependency to Scala standard library

crossPaths in ThisBuild := false

Release.publishSettings

licenses in ThisBuild := Seq("Apache" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

homepage in ThisBuild := Some(url("https://github.com/sphereio/sphere-jvm-sdk"))

javacOptions in (Compile, doc) in ThisBuild := Seq("-quiet", "-overview", "documentation-resources/javadoc-overview.html", "-notimestamp", "-taglet", "CodeTaglet", "-taglet", "DocumentationTaglet",
  "-tagletpath", "./project/target/scala-2.10/sbt-0.13/classes",
  "-bottom", """<link rel='stylesheet' href='http://yandex.st/highlightjs/7.4/styles/default.min.css'><script src='http://yandex.st/highlightjs/7.4/highlight.min.js'></script><script>hljs.initHighlightingOnLoad();</script><style>code {font-size: 1.0em;font-family: monospace;}</style><script src='https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js'></script>""" +
    """\n<span id='custom-javascripts'></span>\n<script>var pathPrefix = $(\".navList a[href$='index-all.html']\").attr(\"href\").replace(\"index-all.html\", \"\"); var closingScriptTag = \"</\" + \"script>\"; \n$('#custom-javascripts').append(\"<script src='\" + pathPrefix + \"documentation-resources/javascripts/main.js'>\" + closingScriptTag + \"<link rel='stylesheet' href='\" + pathPrefix + \"documentation-resources/stylesheets/main.css'>\");</script>""",
  "-encoding", "UTF-8", "-charset", "UTF-8", "-docencoding", "UTF-8")

javacOptions in ThisBuild ++= Seq("-deprecation", "-Xlint:unchecked", "-source", "1.8", "-target", "1.8", "-Xlint:all", "-Xlint:-options", "-Xlint:-path", "-Werror", "-parameters")

genjavadocSettings

releaseSettings

Release.publishSettings