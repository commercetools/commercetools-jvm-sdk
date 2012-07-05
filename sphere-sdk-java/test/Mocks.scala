package sphere

import util.OAuthClient

import com.ning.http.client.AsyncHttpClient
import de.commercetools.sphere.client.SphereShopClient

import play.libs.F.Promise
import play.libs.WS
import play.api.libs.concurrent.PurePromise

object Mocks {

  class MockRequestHolder(status: Int = 200, body: String) extends play.libs.WS.WSRequestHolder("") {
    val response = new WS.Response(null) {
      override def getStatus = status
      override def getBody = body
    }
    override def get(): Promise[WS.Response] = {
      new Promise[WS.Response](PurePromise(response))
    }
    override def post(body: String): Promise[WS.Response] = {
      new Promise[WS.Response](PurePromise(response))
    }
  }

  class MockOAuthClient(status: Int = 200, body: String) extends OAuthClient {
    override def createRequestHolder(url: String): WS.WSRequestHolder = {
      new Mocks.MockRequestHolder(status, body)
    }
  }

  val credentials = new ClientCredentials("", "", "", "", null) {
    override def accessToken: String = "fakeToken"
  }

  val endpoints = new ProjectEndpoints("")

  val shopClient = new SphereShopClient(new AsyncHttpClient, Config.root.shopClientConfig())

  def mockProducts(responseBody: String) = new DefaultProducts(credentials, endpoints) {
    override def createRequestHolder(url: String) = new MockRequestHolder(body = responseBody)
  }

  def mockProductDefinitions(responseBody: String) = new DefaultProductDefinitions(credentials, endpoints) {
    override def createRequestHolder(url: String) = new MockRequestHolder(body = responseBody)
  }

  def mockCategories(responseBody: String) = new DefaultCategories(credentials, endpoints) {
    override def createRequestHolder(url: String) = new MockRequestHolder(body = responseBody)
  }
}
