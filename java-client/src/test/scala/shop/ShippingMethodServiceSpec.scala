package io.sphere.client
package shop

import java.util.Currency
import java.net.URLDecoder

import io.sphere.client.shop.model._
import io.sphere.internal.request._
import io.sphere.internal.request.QueryRequestImpl
import org.scalatest._

import com.neovisionaries.i18n.CountryCode

class ShippingMethodServiceSpec extends WordSpec with MustMatchers {

  import JsonResponses._

  val sphere = MockSphereClient.create(shippingMethodsResponse = FakeResponse(shippingMethodJson))

  // downcast to be able to test some request properties which are not public for shop developers
  private def asImpl(req: FetchRequest[ShippingMethod]) = req.asInstanceOf[FetchRequestImpl[ShippingMethod]]
  private def asImpl(req: QueryRequest[ShippingMethod]) = req.asInstanceOf[QueryRequestImpl[ShippingMethod]]

  "Get all shipping methods" in {
    val shopClient = MockSphereClient.create(shippingMethodsResponse = FakeResponse("{}"))
    shopClient.shippingMethods.all().fetch.getCount must be(0)
  }

  "Get review byId" in {
    val req = sphere.shippingMethods.byId(shippingMethodId)
    URLDecoder.decode(asImpl(req).getRequestHolder.getUrl, "UTF-8") must be (
      "/shipping-methods/" + shippingMethodId + "?expand=zoneRates[*].zone")
    val review = req.fetch()
    review.get.getId must be(shippingMethodId)
  }


  "Get shipping methods by location" in {
    val eur = Currency.getInstance("EUR") 
    val req = MockSphereClient.create(shippingMethodsResponse = FakeResponse("[]")).shippingMethods().byLocation(new Location(CountryCode.DE), eur)
    URLDecoder.decode(req.asInstanceOf[FetchRequestImpl[List[ShippingMethod]]].getRequestHolder.getUrl, "UTF-8") must be (
      "/shipping-methods?country=" + CountryCode.DE.getAlpha2 + "&currency=" + eur.toString + "&expand=zoneRates[*].zone")
    req.fetch().get().size() must be (0)
  }
}
