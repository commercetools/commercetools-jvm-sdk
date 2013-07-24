package sphere

import org.scalatest._
import play.api.test._
import play.api.test.Helpers._
import io.sphere.client.shop.ApiMode
import io.sphere.client.shop.model.Cart
import java.util.Currency

class ConfigSpec extends WordSpec with MustMatchers {

  val config = Map(
    "sphere.auth"         -> "http://localhost:7777",
    "sphere.core"         -> "configDoesNotValidateURLs",
    "sphere.clientId"     -> "client1",
    "sphere.clientSecret" -> "secret1",
    "sphere.project"      -> "project1",
    "sphere.cart.currency" -> "USD",
    "sphere.cart.inventoryMode" -> "TrackOnly",
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
      config.cartCurrency.getSymbol must be (Currency.getInstance("USD").getSymbol)//$ on en locale, USD on de locale
      config.cartInventoryMode must be (Cart.InventoryMode.TrackOnly)
      val shopConfig = config.createSphereClientConfig
      shopConfig.getAuthHttpServiceUrl must be ("http://localhost:7777")
      shopConfig.getCoreHttpServiceUrl must be ("configDoesNotValidateURLs")
      shopConfig.getClientId must be ("client1")
      shopConfig.getClientSecret must be ("secret1")
      shopConfig.getProjectKey must be ("project1")
    }
  }

  "Read apiMode" in {
    running(app(config + ("sphere.products.mode" -> "published"))) {
      sphereConfig.apiMode must be (ApiMode.Published)
      sphereConfig.createSphereClientConfig.getApiMode must be (ApiMode.Published)
    }
    running(app(config + ("sphere.products.mode" -> "staged"))) {
      sphereConfig.apiMode must be (ApiMode.Staged)
      sphereConfig.createSphereClientConfig.getApiMode must be (ApiMode.Staged)
    }
    running(app(config)) {
      sphereConfig.apiMode must be (ApiMode.Published)
      sphereConfig.createSphereClientConfig.getApiMode must be (ApiMode.Published)
    }
    running(app(config + ("sphere.products.mode" -> "awesome"))) {
      val e = intercept[Exception] {
        sphereConfig.apiMode
      }
      e.getMessage must include ("'sphere.products.mode' must be \"published\" or \"staged\". Was \"awesome\".")
    }
  }

  // TODO pass chaosLevel to ShopClientConfig
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
      intercept[Exception] {
        sphereConfig.chaosLevel
      }
    }
  }

  "Read cart.inventorMode" in {
    running(app(config - "sphere.cart.inventoryMode")) {
      sphereConfig.cartInventoryMode must be (Cart.InventoryMode.None)
    }
    running(app(config + ("sphere.cart.inventoryMode" -> "track"))) {
      val e = intercept[Exception] {
        sphereConfig.cartInventoryMode
      }
      e.getMessage must be (
        "Configuration error[Invalid value for cart.inventoryMode: 'track'. Valid values are: TrackOnly, ReserveOnOrder, None]")
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

  "Validate project key" in {
    running(app(config + ("sphere.project" -> "ab$"))) {
      val e = intercept[Exception] {
        sphereConfig.createSphereClientConfig
      }
      e.getMessage must be (
        "Configuration error[Invalid project key: 'ab$'. Project keys can contain alphanumeric characters, dashes and underscores.]")
    }
  }
}
