package sphere

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import play.api.test._
import play.api.test.Helpers._

class ProductAPISpec extends WordSpec with MustMatchers {

  val config = Map(
    "sphere.auth" -> "http://localhost:7777",
    "sphere.core" -> "http://localhost:4242",
    "sphere.clientID" -> "9bOVK9p4-hU_FzW5mR7iWlOX",
    "sphere.clientSecret" -> "hztKsSJmdfNjfR8N7da1fZ9YRZHp0yZ-",
    "sphere.project" -> "a381e84b-5a0c-4b83-badd-76f6e108486e")

  val mockClientCredentials = new ClientCredentials("", "", "", "") {
    override def accessToken: String = "fakeToken"
  }

  "Get all products" in {
    running(FakeApplication(additionalConfiguration = config)) {
      val sphere = new Sphere(new Config(play.Configuration.root()), mockClientCredentials)
      println(sphere.products)
    }
  }
}
