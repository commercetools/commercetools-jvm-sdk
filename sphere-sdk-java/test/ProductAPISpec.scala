package sphere

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import play.api.test._
import play.api.test.Helpers._
import play.libs.F.Promise
import play.libs.WS
import play.api.libs.concurrent.PurePromise

class MockRequestHolder(status: Int = 200, body: String) extends play.libs.WS.WSRequestHolder("") {
  val response = new WS.Response(null) {
    override def getStatus = status
    override def getBody = body
  }
  override def get(): Promise[WS.Response] = {
    new Promise[WS.Response](PurePromise(response))
  }
}

class ProductAPISpec extends WordSpec with MustMatchers {

  val credentials = new ClientCredentials("", "", "", "") {
    override def accessToken: String = "fakeToken"
  }
  val endpoints = new ProjectEndpoints("")

  def mockProducts(responseBody: String) = new DefaultProducts(credentials, endpoints) {
    override def createRequestHolder(url: String) = new MockRequestHolder(body = responseBody)
  }
  def mockProductDefinitions(responseBody: String) = new DefaultProductDefinitions(credentials, endpoints) {
    override def createRequestHolder(url: String) = new MockRequestHolder(body = responseBody)
  }
  def mockCategories(responseBody: String) = new DefaultCategories(credentials, endpoints) {
    override def createRequestHolder(url: String) = new MockRequestHolder(body = responseBody)
  }

  def mockSphere(fakeBackendResponse: String) = new Sphere(
    credentials,
    mockProducts(fakeBackendResponse),
    mockProductDefinitions(fakeBackendResponse),
    mockCategories(fakeBackendResponse))

  "Get all products" in {
    running(FakeApplication()) {
      val responseBody = "{}"
      val sphere = mockSphere(responseBody)
      println(sphere.products.getAll.get)
    }
  }
}
