package sphere

import org.scalatest._
import actors.Futures
import java.util.concurrent._
import java.util.Properties
import java.io.{File, FileInputStream}

/** Starts up backend web services locally, runs shop/SDK integration tests against them and then kills them. */
abstract class IntegrationSpec(webserviceNames: String*) extends WordSpec with BeforeAndAfterAll {

  /** Configuration of integration tests. */
  val integrationTestConfigFile = "integration/integrationTest.properties"
  /** String to recognize in output of a webservice to see when it's been started. */
  val watchOutputLine = "Startup of HTTP server complete."
  /** Script to start up backend web services. */
  val wsStartScriptPath = "integration/ws-start.sh"
  /** Script to kill up backend web services. */
  val wsKillScriptPath  = "integration/ws-kill.sh"

  val proc = scala.sys.process.stringToProcess _

  lazy val properties: Properties = {
    val props = new Properties()
    props.load(new FileInputStream(new File(integrationTestConfigFile)))
    props
  }

  lazy val sphereBackendPath = Option(properties.getProperty("sphere.backendPath")).getOrElse(
    throw new RuntimeException("Cannot run integration tests: please provide sphere.backendPath in integration/integrationTest.propeties file.")
  )

  val defaultWsStartupTimeoutSeconds = 30L
  lazy val wsStartupTimeoutSeconds = Option(properties.getProperty("sphere.integrationTestTimeoutSec")).
    map(timeout => try { timeout.toLong } catch {
      case e: NumberFormatException => throw new RuntimeException("-Dsphere.integrationTestTimeoutSec must be a number: " + timeout) }).
    getOrElse(defaultWsStartupTimeoutSeconds)

  def killWebservice(name: String) = {
    proc(wsKillScriptPath + " " + name).!
  }

  override def beforeAll() {
    super.beforeAll()
    // kill any webservices that accidentally survived from last run
    webserviceNames.foreach { wsName =>
      killWebservice(wsName)
    }
    webserviceNames.foreach { name =>
      val lines = proc(wsStartScriptPath + " %s %s".format(name, sphereBackendPath)).lines
      val future = Executors.newSingleThreadExecutor().submit(new Runnable {
        def run() { lines.takeWhile(!_.contains(watchOutputLine)).foreach(println) }
      })
      try {
        future.get(wsStartupTimeoutSeconds, TimeUnit.SECONDS)
      } catch {
        case e: TimeoutException => fail("Startup of webservice " + name + " timed out.")
      }
      sphere.Log.debug("Integration test: webservice " + name + " started successfully.")
    }
  }

  override def afterAll() {
    super.afterAll()
    webserviceNames.foreach { wsName =>
      killWebservice(wsName)
    }
  }
}
