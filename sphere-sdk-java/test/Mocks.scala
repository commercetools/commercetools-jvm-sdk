package sphere

import com.ning.http.client.AsyncHttpClient
import util.OAuthClient
import de.commercetools.sphere.client.shop.ShopClient

import play.libs.WS
import play.libs.F.Promise
import play.api.libs.concurrent.PurePromise
import org.codehaus.jackson.`type`.TypeReference

object Mocks {

  class MockOAuthClient(status: Int = 200, body: String) extends OAuthClient {
    override def createRequestHolder(url: String): WS.WSRequestHolder = {
      new Mocks.MockRequestHolder(status, body)
    }
  }

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

  val credentials = new ClientCredentials("", "", "", "", null) {
    override def accessToken: String = "fakeToken"
  }

  val endpoints = new ProjectEndpoints("")

  val shopClient = new ShopClient(new AsyncHttpClient, Config.root.shopClientConfig())

  def mockProducts(responseBody: String) = new DefaultProducts(credentials, endpoints) {
    override def requestBuilder[T](url: String, jsonParserTypeRef: TypeReference[T]) =
      new MockRequestBuilder[T](responseBody, jsonParserTypeRef)
  }

  def mockProductDefinitions(responseBody: String) = new DefaultProductTypes(credentials, endpoints) {
    override def requestBuilder[T](url: String, jsonParserTypeRef: TypeReference[T]) =
      new MockRequestBuilder[T](responseBody, jsonParserTypeRef)
  }

  def mockCategories(responseBody: String) = new DefaultCategories(credentials, endpoints) {
    override def requestBuilder[T](url: String, jsonParserTypeRef: TypeReference[T]) =
      new MockRequestBuilder[T](responseBody, jsonParserTypeRef)
  }
}
