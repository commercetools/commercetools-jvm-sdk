package de.commercetools.sphere.client
package shop

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

class ProductsSpec extends WordSpec with MustMatchers {

  "Get all products" in {
    val fakeBackendResponse = "{}"
    Mocks.mockShopClient(fakeBackendResponse).products.all().fetch.getCount must be(0)
  }
}
