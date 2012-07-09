package sphere

import org.scalatest._
import java.util.concurrent._
import java.util.Properties
import java.io.{File, FileInputStream}

/** Starts up backend web services locally, runs shop/SDK integration tests against them and then kills them. */
abstract class IntegrationSpec(webserviceNames: String*) extends WordSpec with BeforeAndAfterAll {

  /** Configuration of integration tests. */
  val integrationTestConfigFile = "integration/integrationTest.properties"
  /** String to wait for in webservice's stdout when starting it. */
  val watchOutputLine = "Startup of HTTP server complete."
  /** Script to start backend web services. */
  val wsStartScriptPath = "integration/ws-start.sh"
  /** Script to kill backend web services. */
  val wsKillScriptPath  = "integration/ws-kill.sh"

  val proc = scala.sys.process.stringToProcess _

  lazy val properties: Properties = {
    val props = new Properties()
    props.load(new FileInputStream(new File(integrationTestConfigFile)))
    props
  }

  lazy val sphereBackendPath = Option(properties.getProperty("sphere.backendPath")).getOrElse(
    throw new RuntimeException(
      "Cannot run integration tests: please provide sphere.backendPath in sphere-applications/integration/integrationTest.propeties file.")
  )

  /** Timeout for starting each backend webservice. */
  lazy val wsStartupTimeoutSec = getConfigLongValue("sphere.integrationTestTimeoutSec", default = 30L)
  /** Timeout for each requests to backend webservice. */
  lazy val wsRequestTimeoutSec = getConfigLongValue("sphere.integrationTestRequestTimeoutSec", default = 30L)

  def getConfigLongValue(key: String, default: Long) = Option(properties.getProperty(key)).
    map(value => try { value.toLong } catch {
    case e: NumberFormatException => throw new RuntimeException(
      "Cannot parse number from sphere-applications/integration/integrationTest.propeties file: " + key + ": " + value) }).
    getOrElse(default)

  def startWebservice(name: String) = {
    val lines = proc(wsStartScriptPath + " %s %s".format(name, sphereBackendPath)).lines
    var success = false
    val future = Executors.newSingleThreadExecutor().submit(new Runnable {
      def run() { lines.takeWhile(line => { success = line.contains(watchOutputLine); !success }).foreach(println) }
    })
    try {
      future.get(wsStartupTimeoutSec, TimeUnit.SECONDS)
    } catch {
      case e: TimeoutException => fail("Startup of webservice " + name + " timed out.")
    }
    if (!success) {
      throw new RuntimeException("Integration test: couldn't start webservice " + name + ". See previous lines for details.")
    }
    sphere.Log.debug("Integration test: webservice " + name + " started successfully.")
  }

  def killWebservice(name: String): Int = {
    proc(wsKillScriptPath + " " + name).!
  }

  override def beforeAll() {
    super.beforeAll()
    // kill any webservices that accidentally survived from last run
    webserviceNames foreach killWebservice
    webserviceNames foreach startWebservice
  }

  override def afterAll() {
    super.afterAll()
    webserviceNames foreach killWebservice
  }
}
