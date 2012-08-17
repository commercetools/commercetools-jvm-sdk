package sphere

import de.commercetools.sphere.client.shop.ShopClient
import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import play.api.test._
import play.api.test.Helpers._

class ProductAPISpec extends WordSpec with MustMatchers {

  val fakeConfig = Map(
    "sphere.core" -> "http://localhost:4242/",
    "sphere.auth" -> "http://localhost:4343/",
    "sphere.project" -> "test-project",
    "sphere.clientID" -> "test-client",
    "sphere.clientSecret" -> "test-secret")

  def mockShopClient(fakeBackendResponse: String) = new ShopClient(
    Config.root.shopClientConfig,
    Mocks.credentials,
    Mocks.mockProducts(fakeBackendResponse),
    Mocks.mockCategories(fakeBackendResponse))

  "Get all products" in {
    running(FakeApplication(additionalConfiguration = fakeConfig)) {
      val responseBody = "{}"
      val shopClient = mockShopClient(responseBody)
      shopClient.getProducts.all().fetch.getCount must be(0)
    }
  }
}
