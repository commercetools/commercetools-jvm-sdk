package de.commercetools.sphere.client

import de.commercetools.internal._
import de.commercetools.sphere.client.shop._
import de.commercetools.internal.request._

case class FakeResponse(body: String, statusCode: Int = 200)

object MockShopClient {
  // endpoints based on an empty backend url -> "/products", "/carts/:id" etc.
  private val endpoints = new ProjectEndpoints("")

  private def nullResponse(serviceName: String): FakeResponse =
    FakeResponse("[tests] " + serviceName + " endpoint wasn't expected to be used by the MockShopClient.", 400)

  private def reqFactory(fakeResponse: FakeResponse): RequestFactory = {
    new MockRequestFactory(fakeResponse.body, fakeResponse.statusCode)
  }

  /** Creates a shop client with mocked backend that returns preconfigured responses for individual services. */
  def create(
    productsResponse:   FakeResponse = nullResponse("Products"),
    categoriesResponse: FakeResponse = nullResponse("Categories"),
    cartsResponse:      FakeResponse = nullResponse("Carts"),
    ordersResponse:     FakeResponse = nullResponse("Orders"),
    customersResponse:  FakeResponse = nullResponse("Customers")) =
  {
    val categoryTree = CategoryTreeImpl.createAndBeginBuildInBackground(new CategoriesImpl(reqFactory(categoriesResponse), endpoints))
    new ShopClient(
      new ShopClientConfig.Builder("projectKey", "clientId", "clientSecret").build,
      new ProductsImpl(new ProductRequestFactoryImpl(reqFactory(productsResponse), categoryTree), endpoints),
      categoryTree,
      new CartsImpl(reqFactory(cartsResponse), endpoints),
      new OrdersImpl(reqFactory(ordersResponse), endpoints),
      new CustomersImpl(reqFactory(customersResponse), endpoints))
  }
}
