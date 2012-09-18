package de.commercetools.sphere.client
package shop

class ProductAPISpec extends MockedShopClientSpec {

  "Get all products" in {
    val responseBody = "{}"
    val shopClient = mockShopClient(responseBody)
    shopClient.products.all().fetch.getCount must be(0)
  }
}
