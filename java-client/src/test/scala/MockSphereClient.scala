package io.sphere.client

import io.sphere.internal._
import io.sphere.client.shop._
import io.sphere.internal.request._

case class FakeResponse(body: String, statusCode: Int = 200)

object MockSphereClient {
  // endpoints based on an empty backend url -> "/products", "/carts/:id" etc.
  private val endpoints = new ProjectEndpoints("")

  private def nullResponse(serviceName: String): FakeResponse =
    FakeResponse("[tests] " + serviceName + " endpoint wasn't expected to be used by the MockSphereClient.", 400)

  private def reqFactory(fakeResponse: FakeResponse): RequestFactory = {
    new RequestFactoryImpl(new MockBasicRequestFactory(fakeResponse.body, fakeResponse.statusCode))
  }

  /** Creates a shop client with mocked backend that returns pre-configured responses for individual services. */
  def create(
    apiMode: ApiMode = ApiMode.Staged,
    productsResponse:   FakeResponse = nullResponse("Products"),
    categoriesResponse: FakeResponse = nullResponse("Categories"),
    cartsResponse:      FakeResponse = nullResponse("Carts"),
    ordersResponse:     FakeResponse = nullResponse("Orders"),
    customersResponse:  FakeResponse = nullResponse("Customers"),
    commentsResponse:   FakeResponse = nullResponse("Comments"),
    reviewsResponse:    FakeResponse = nullResponse("Reviews"),
    inventoryResponse:  FakeResponse = nullResponse("Inventory"),
    shippingMethodsResponse: FakeResponse = nullResponse("ShippingMethods"),
    taxCategoriesResponse: FakeResponse = nullResponse("TaxCategories")): SphereClient =
  {
    val categoryTree = CategoryTreeImpl.createAndBeginBuildInBackground(new CategoriesImpl(reqFactory(categoriesResponse), endpoints))
    new SphereClient(
      new SphereClientConfig.Builder("projectKey", "clientId", "clientSecret").build,
      /*HttpClient*/null, /*ClientCredentials*/null,
      new ProductServiceImpl(new ProductRequestFactoryImpl(reqFactory(productsResponse), categoryTree), apiMode, endpoints),
      categoryTree,
      new CartServiceImpl(reqFactory(cartsResponse), endpoints),
      new OrderServiceImpl(reqFactory(ordersResponse), endpoints),
      new CustomerServiceImpl(reqFactory(customersResponse), endpoints),
      new CommentServiceImpl(reqFactory(commentsResponse), endpoints),
      new ReviewServiceImpl(reqFactory(reviewsResponse), endpoints),
      new InventoryServiceImpl(reqFactory(inventoryResponse), endpoints),
      new ShippingMethodServiceImpl(reqFactory(shippingMethodsResponse), endpoints),
      new TaxCategoryServiceImpl(reqFactory(taxCategoriesResponse), endpoints))
  }
}
