import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  lazy val main = PlayProject("sphere-applications") dependsOn(sampleStore) aggregate (sampleStore)

  lazy val sampleStore = PlayProject(
    "sample-store", "1.0-SNAPSHOT",
    path = file("sample-store-java"),
    mainLang = JAVA
  ).dependsOn(sdk % "compile->compile;test->test").aggregate(sdk).settings(
    testSettings:_*
  )

  lazy val sdk = PlayProject(
    "sphere-sdk", "1.0-SNAPSHOT", dependencies = Seq(), path = file("sphere-sdk-java")
  ).settings(Seq(
    organization := "de.commercetools",
    scalaVersion := "2.9.1",
    libraryDependencies ++= Seq(Libs.commonsCodec, Libs.commonsIO, Libs.guice))
    ++ testSettings:_*
  )

  lazy val testSettings = Seq[Setting[_]](
    parallelExecution in Test := false,  // Play function tests crash when run in parallel
    testListeners <<= target.map(t => Seq(new OriginalXmlTestsListener(t.getAbsolutePath))),
    libraryDependencies ++= Seq(Libs.scalacheck, Libs.scalatest),
    testOptions in Test := Seq(
      //Tests.Argument(TestFrameworks.ScalaTest, "-l", "disabled integration"),
      Tests.Argument(TestFrameworks.ScalaTest, "-oD")) // show durations
  )
}

object Libs {
  lazy val commonsCodec = "commons-codec" % "commons-codec" % "1.5"
  lazy val commonsIO = "commons-io" % "commons-io" % "2.3"
  lazy val guice = "com.google.inject" % "guice" % "3.0"

  lazy val scalatest = "org.scalatest" %% "scalatest" % "1.7.1" % "test"
  lazy val scalacheck = "org.scala-tools.testing" %% "scalacheck" % "1.9" % "test"
}

// To get around Play having its (not-working) JUnitXmlTestListener in the original eu.henkelmann package: http://bit.ly/MXrEmY
// https://github.com/hydrasi/junit_xml_listener/blob/master/src/main/scala/eu/henkelmann/sbt/JUnitXmlTestsListener.scala
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