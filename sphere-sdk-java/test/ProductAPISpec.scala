package sphere

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import play.api.test._
import play.api.test.Helpers._

class ProductAPISpec extends WordSpec with MustMatchers {

  def mockSphere(fakeBackendResponse: String) = new Sphere(
    Mocks.credentials,
    Mocks.mockProducts(fakeBackendResponse),
    Mocks.mockProductDefinitions(fakeBackendResponse),
    Mocks.mockCategories(fakeBackendResponse))

  "Get all products" in {
    running(FakeApplication()) {
      val responseBody = "{}"
      val sphere = mockSphere(responseBody)
      sphere.products.getAll.get.getCount must be(0)
    }
  }
}
