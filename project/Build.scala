import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  lazy val main = play.Project("sphere-applications").
    aggregate(sampleStore, sphereSDK, sphereJavaClient)

  lazy val standardSettings = Seq(
    organization := "de.commercetools",
    scalaVersion := "2.10.0",
    scalacOptions ++= Seq("-deprecation", "-unchecked"), // emit warnings for deprecated APIs, emit erasure warnings
    publishArtifact in (Compile, packageDoc) := false    // don't publish Scaladoc (will use a javadoc plugin to generate javadoc)
  )

  /** Compile the SDK for Java 6, so that it for developers who're still on Java 6. */
  lazy val java6Settings = Seq[Setting[_]](
    javacOptions ++= Seq("-source", "1.6", "-target", "1.6")
  )

  def testSettings(testLibs: ModuleID*) = Seq[Setting[_]](
    parallelExecution in Test := false,  // Play functional tests crash when run in parallel
    testListeners <<= target.map(t => Seq(new OriginalXmlTestsListener(t.getAbsolutePath))),
    libraryDependencies ++= Seq(testLibs:_*),
    testOptions in Test := Seq(
      //Tests.Argument(TestFrameworks.ScalaTest, "-l", "disabled integration"),
      Tests.Argument(TestFrameworks.ScalaTest, "-oD")) // show durations
  )

  // Add these to a project to be able to use the sbt 'publish' task to publish to ct Nexus.
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

  lazy val sampleStore = play.Project(
    "sample-store",
    "1.0-SNAPSHOT",
    Seq(javaCore),
    path = file("sample-store-java")
  ).dependsOn(sphereSDK % "compile->compile;test->test").aggregate(sphereSDK, sphereJavaClient).
    settings(standardSettings:_*).
    settings(testSettings(Libs.scalatest):_*).
    settings(Seq(
      templatesImport ++= Seq(
        "util._",
        "de.commercetools.sphere.client.shop.model._",
        "de.commercetools.sphere.client.model._")):_*)

  lazy val sphereSDK = play.Project(
    "sphere-sdk",
    "0.25-SNAPSHOT",
    Seq(javaCore),
    path = file("sphere-sdk-java")
  // aggregate: clean, compile, publish etc. transitively
  ).dependsOn(sphereJavaClient % "compile->compile;test->test").aggregate(sphereJavaClient).
    settings(standardSettings:_*).
    settings(java6Settings:_*).
    settings(testSettings(Libs.scalatest, Libs.scalamock):_*).
    settings(publishSettings:_*)

  lazy val sphereJavaClient = Project(
    id = "sphere-java-client",
    base = file("sphere-java-client"),

    settings =
      standardSettings ++ java6Settings ++ testSettings(Libs.scalatest, Libs.scalamock) ++
      publishSettings ++ Defaults.defaultSettings ++ Seq(
        version := "0.25-SNAPSHOT",
        autoScalaLibrary := true, // no dependency on Scala standard library (just for tests)
        crossPaths := false,
        libraryDependencies ++= Seq(
          Libs.asyncHttpClient, Libs.guava, Libs.jodaTime, Libs.jodaConvert, Libs.jackson, Libs.jacksonMapper, Libs.jcip,
          Libs.commonsCodec, // Base64 for OAuth client
          Libs.nvI18n // CountryCode
        )))
}

object Libs {
  lazy val asyncHttpClient = "com.ning" % "async-http-client" % "1.7.5"
  lazy val guava           = "com.google.guava" % "guava" % "12.0"
  lazy val jodaTime        = "joda-time" % "joda-time" % "2.1"
  lazy val jodaConvert     = "org.joda" % "joda-convert" % "1.1"
  lazy val jackson         = "org.codehaus.jackson" % "jackson-core-asl" % "1.9.9"
  lazy val jacksonMapper   = "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.9"
  lazy val jcip            = "net.jcip" % "jcip-annotations" % "1.0"
  lazy val commonsCodec    = "commons-codec" % "commons-codec" % "1.5"
  lazy val nvI18n          = "com.neovisionaries" % "nv-i18n" % "1.4"

  lazy val scalatest       = "org.scalatest" %% "scalatest" % "1.7.1" % "test"
  lazy val scalamock       = "org.scalamock" %% "scalamock-scalatest-support" % "3.0.1"
}

// To get around Play having its (not-working) JUnitXmlTestListener in the original eu.henkelmann package: http://bit.ly/MXrEmY
/**
 * A tests listener that outputs the results it receives in junit xml
 * report format.
 * @param outputDir path to the dir in which a folder with results is generated
 */
class OriginalXmlTestsListener(val outputDir:String) extends TestsListener
{
  import java.io.{StringWriter, PrintWriter, File}
  import java.net.InetAddress
  import scala.collection.mutable.ListBuffer
  import scala.xml.{Elem, Node, XML}
  import org.scalatools.testing.{Event => TEvent, Result => TResult, Logger => TLogger}

  /**Current hostname so we know which machine executed the tests*/
  val hostname = InetAddress.getLocalHost.getHostName
  /**The dir in which we put all result files. Is equal to the given dir + "/test-reports"*/
  val targetDir = new File(outputDir + "/test-reports/")

  /**all system properties as XML*/
  val properties =
    <properties> {
      val iter = System.getProperties.entrySet.iterator
      val props:ListBuffer[Node] = new ListBuffer()
      while (iter.hasNext) {
        val next = iter.next
        props += <property name={next.getKey.toString} value={next.getValue.toString} />
      }
      props
      }
    </properties>

  /** Gathers data for one Test Suite. We map test groups to TestSuites.
   * Each TestSuite gets its own output file.
   */
  class TestSuite(val name:String) {
    val events:ListBuffer[TEvent] = new ListBuffer()
    val start                     = System.currentTimeMillis
    var end                       = System.currentTimeMillis

    /**Adds one test result to this suite.*/
    def addEvent(e:TEvent) = events += e

    /** Returns a triplet with the number of errors, failures and the
     * total numbers of tests in this suite.
     */
    def count():(Int, Int, Int) = {
      var errors, failures = 0
      for (e <- events) {
        e.result match {
          case TResult.Error   => errors +=1
          case TResult.Failure => failures +=1
          case _               =>
        }
      }
      (errors, failures, events.size)
    }

    /** Stops the time measuring and emits the XML for
     * All tests collected so far.
     */
    def stop():Elem = {
      end = System.currentTimeMillis
      val duration  = end - start

      val (errors, failures, tests) = count()

      val result = <testsuite hostname={hostname} name={name}
                              tests={tests + ""} errors={errors + ""} failures={failures + ""}
                              time={(duration/1000.0).toString} >
        {properties}
        {
        for (e <- events) yield
          <testcase classname={name} name={e.testName} time={"0.0"}> {
            var trace:String = if (e.error!=null) {
              val stringWriter = new StringWriter()
              val writer = new PrintWriter(stringWriter)
              e.error.printStackTrace(writer)
              writer.flush()
              stringWriter.toString
            }
            else {
              ""
            }
            e.result match {
              case TResult.Error   if (e.error!=null) => <error message={e.error.getMessage} type={e.error.getClass.getName}>{trace}</error>
              case TResult.Error                      => <error message={"No Exception or message provided"} />
              case TResult.Failure if (e.error!=null) => <failure message={e.error.getMessage} type={e.error.getClass.getName}>{trace}</failure>
              case TResult.Failure                    => <failure message={"No Exception or message provided"} />
              case TResult.Skipped                    => <skipped />
              case _               => {}
            }
            }
          </testcase>

        }
        <system-out><![CDATA[]]></system-out>
        <system-err><![CDATA[]]></system-err>
      </testsuite>

      result
    }
  }

  /**The currently running test suite*/
  var testSuite:TestSuite = null

  /**Creates the output Dir*/
  override def doInit() = {targetDir.mkdirs()}

  /** Starts a new, initially empty Suite with the given name.
   */
  override def startGroup(name: String) {testSuite = new TestSuite(name)}

  /** Adds all details for the given even to the current suite.
   */
  override def testEvent(event: TestEvent): Unit = for (e <- event.detail) {testSuite.addEvent(e)}

  override def endGroup(name: String, t: Throwable) = {
    System.err.println("Throwable escaped the test run of '" + name + "': " + t)
    t.printStackTrace(System.err)
  }

  /** Ends the current suite, wraps up the result and writes it to an XML file
   *  in the output folder that is named after the suite.
   */
  override def endGroup(name: String, result: TestResult.Value) = {
    XML.save (new File(targetDir, testSuite.name + ".xml").getAbsolutePath, testSuite.stop(), "UTF-8", true, null)
  }

  /**Does nothing, as we write each file after a suite is done.*/
  override def doComplete(finalResult: TestResult.Value): Unit = {}

  /**Returns None*/
  override def contentLogger(test: TestDefinition): Option[ContentLogger] = None
}
