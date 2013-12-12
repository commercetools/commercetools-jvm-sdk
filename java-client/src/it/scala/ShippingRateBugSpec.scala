package sphere

import io.sphere.client.shop.model._
import TestData._
import IntegrationTest.Implicits._
import org.scalatest._
import io.sphere.client.shop.SphereClient
import scala.collection.JavaConversions._

class ShippingRateBugSpec extends WordSpec with MustMatchers {
  implicit lazy val client: SphereClient = IntegrationTestClient()

  "sphere client" must {
    "expand the shipping zone" in {
      val shippingMethods = client.shippingMethods.query.fetch.getResults
      shippingMethods.map(_.getZoneRates).exists(_.exists(_.getZone.isExpanded)) must be (true)
    }

    "get the shipping rate" in {
      val shippingMethods = client.shippingMethods.query.fetch.getResults
      val cart = newCartWithProduct
      val location = Location.of(cart.getShippingAddress)
      val currency = cart.getCurrency
      shippingMethods.map(_.shippingRateForLocation(location, currency)) must contain(new ShippingRate(EUR("20.0"), EUR("500.0")))
    }
  }
}