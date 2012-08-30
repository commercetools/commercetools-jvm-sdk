package de.commercetools.sphere.client
package shop

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

class ProductAPISpec extends WordSpec with MustMatchers {

  def mockShopClient(fakeBackendResponse: String) = new ShopClient(
    new ShopClientConfig.Builder("projectKey", "clientId", "clientSecret").build,
    Mocks.credentials,
    Mocks.mockProducts(fakeBackendResponse),
    Mocks.mockCategories(fakeBackendResponse))

  "Get all products" in {
    val responseBody = "{}"
    val shopClient = mockShopClient(responseBody)
    shopClient.products.all().fetch.getCount must be(0)
  }
}
