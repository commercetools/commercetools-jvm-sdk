package de.commercetools.sphere.client
package shop

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

class MockedShopClientSpec extends WordSpec with MustMatchers {

  def mockShopClient(fakeBackendResponse: String, fakeResponseStatusCode: Int = 200) = new ShopClient(
    new ShopClientConfig.Builder("projectKey", "clientId", "clientSecret").build,
    Mocks.mockProducts(fakeBackendResponse, fakeResponseStatusCode),
    Mocks.mockCategories(fakeBackendResponse, fakeResponseStatusCode),
    Mocks.mockCarts(fakeBackendResponse, fakeResponseStatusCode))

}
