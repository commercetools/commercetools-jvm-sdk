package sphere

import io.sphere.client.shop.model.Customer
import play.mvc.Http
import play.test.Helpers._
import scala.util.Random
import collection.JavaConversions._

object SphereIntegrationTest {
  def withSphere(customer: Option[Customer] = None)(f: Sphere => Unit) = {
    val ConfigMap = {
      val config = IntegrationTestClient().getConfig
      import config._
      Map("application.secret" -> "xyz", "sphere.project" -> getProjectKey,
        "sphere.clientId" -> getClientId, "sphere.clientSecret" -> getClientSecret,
        "sphere.core" -> getCoreHttpServiceUrl, "sphere.auth" -> getAuthHttpServiceUrl,
        "sphere.cart.currency" -> "EUR")
    }
    running(fakeApplication(ConfigMap), new Runnable() {
      def run() {
        val context = new Http.Context(Random.nextLong(), null, null, Map[String, String](),
          Map[String, String](), Map[String, AnyRef]())
        Http.Context.current.set(context)
        customer.foreach(Session.current().putCustomer(_))
        f(Sphere.getInstance())
      }
    })
  }
}
