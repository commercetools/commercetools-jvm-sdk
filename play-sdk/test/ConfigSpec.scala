package sphere

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import play.api.test._
import play.api.test.Helpers._
import io.sphere.client.shop.ApiMode

class ConfigSpec extends WordSpec with MustMatchers {

  val config = Map(
    "sphere.auth"         -> "http://localhost:7777",
    "sphere.core"         -> "configDoesNotValidateURLs",
    "sphere.clientId"     -> "client1",
    "sphere.clientSecret" -> "secret1",
    "sphere.project"      -> "project1",
    "sphere.cartCurrency" -> "USD",
    "unused"              -> "unused")

  def sphereConfig = new SphereConfig(play.Configuration.root)
  def app(conf: Map[String, String]) = FakeApplication(additionalConfiguration = conf)

  "Read config" in {
    running(app(config)) {
      val config = sphereConfig
      config.authEndpoint must be ("http://localhost:7777")
      config.coreEndpoint must be ("configDoesNotValidateURLs")
      config.clientId must be ("client1")
      config.clientSecret must be ("secret1")
      config.project must be ("project1")
      config.cartCurrency.getSymbol must be ("$")
    }
  }

  "Read apiMode" in {
    running(app(config + ("sphere.api.mode" -> "live"))) {
      sphereConfig.apiMode must be (ApiMode.Published)
    }
    running(app(config + ("sphere.api.mode" -> "staging"))) {
      sphereConfig.apiMode must be (ApiMode.Staged)
    }
    running(app(config)) {
      sphereConfig.apiMode must be (ApiMode.Published)
    }
    running(app(config + ("sphere.api.mode" -> "awesome"))) {
      val e = intercept[Exception] {
        sphereConfig.apiMode must be (ApiMode.Published)
      }
      e.getMessage must include ("'sphere.api.mode' must be \"live\" or \"staging\". Was \"awesome\".")
    }
  }

  "Read chaosLevel" in {
    running(app(config)) {
      sphereConfig.chaosLevel must be (0)
    }
    running(app(config + ("sphere.chaosLevel" -> "0"))) {
      sphereConfig.chaosLevel must be (0)
    }
    running(app(config + ("sphere.chaosLevel" -> "-1"))) {
      sphereConfig.chaosLevel must be (0)
    }
    running(app(config + ("sphere.chaosLevel" -> "5"))) {
      sphereConfig.chaosLevel must be (5)
    }
    running(app(config + ("sphere.chaosLevel" -> "6"))) {
      sphereConfig.chaosLevel must be (5)
    }
    running(app(config + ("sphere.chaosLevel" -> "none"))) {
      val e = intercept[Exception] {
        sphereConfig.chaosLevel
      }
    }
  }

  "Fail on missing keys" in {
    running(app(config - "sphere.clientSecret")) {
      val e = intercept[Exception] {
        sphereConfig.clientSecret
      }
      e.getMessage must be("Configuration error[Path 'sphere.clientSecret' not found in configuration.]")
    }
  }
}
