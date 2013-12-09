package sphere

import io.sphere.client.shop.model._
import TestData._
import IntegrationTest.Implicits._
import org.scalatest._
import io.sphere.client.shop.SphereClient

class ShippingRateBugSpec extends WordSpec with MustMatchers {
  implicit lazy val client: SphereClient = IntegrationTestClient()

  "sphere client" must {
    "expand the shipping zone" in {
      val shippingMethods = client.shippingMethods.query.fetch.getResults
      shippingMethods.get(1).getZoneRates.get(0).getZone.isExpanded must be (true)
    }

    "get the shipping rate" in {
      val shippingMethods = client.shippingMethods.query.fetch.getResults
      val cart = newCartWithProduct
      val rate: ShippingRate = shippingMethods.get(1).shippingRateForLocation(Location.of(cart.getShippingAddress), cart.getCurrency())
      rate.getPrice must be (EUR("20.0"))
      rate.getFreeAbove must be (EUR("500.0"))
    }
  }
}