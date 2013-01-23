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
    customersResponse:  FakeResponse = nullResponse("Customers"),
    commentsResponse:   FakeResponse = nullResponse("Comments"),
    reviewsResponse:    FakeResponse = nullResponse("Reviews"),
    inventoryResponse:  FakeResponse = nullResponse("Inventory")): ShopClient =
  {
    val categoryTree = CategoryTreeImpl.createAndBeginBuildInBackground(new CategoriesImpl(reqFactory(categoriesResponse), endpoints))
    new ShopClient(
      new ShopClientConfig.Builder("projectKey", "clientId", "clientSecret").build,
      new ProductServiceImpl(new ProductRequestFactoryImpl(reqFactory(productsResponse), categoryTree), endpoints),
      categoryTree,
      new CartServiceImpl(reqFactory(cartsResponse), endpoints),
      new OrderServiceImpl(reqFactory(ordersResponse), endpoints),
      new CustomerServiceImpl(reqFactory(customersResponse), endpoints),
      new CommentServiceImpl(reqFactory(commentsResponse), endpoints),
      new ReviewServiceImpl(reqFactory(reviewsResponse), endpoints),
      new InventoryServiceImpl(reqFactory(inventoryResponse), endpoints))
  }
}
