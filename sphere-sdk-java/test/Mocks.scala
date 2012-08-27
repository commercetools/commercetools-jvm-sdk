package sphere

import de.commercetools.sphere.client.ProjectEndpoints
import de.commercetools.sphere.client.shop._
import com.ning.http.client.AsyncHttpClient
import org.codehaus.jackson.`type`.TypeReference

object Mocks {

  val credentials = new ClientCredentials {
    override def accessToken: String = "fakeToken"
  }

  val endpoints = new ProjectEndpoints("")

  def mockProducts(responseBody: String) = new DefaultProducts(new AsyncHttpClient(), credentials, endpoints) {
    override def requestBuilder[T](url: String, jsonParserTypeRef: TypeReference[T]) =
      new MockRequestBuilder[T](responseBody, jsonParserTypeRef)
  }

  def mockCategories(responseBody: String) = new DefaultCategories(new AsyncHttpClient(), credentials, endpoints) {
    override def requestBuilder[T](url: String, jsonParserTypeRef: TypeReference[T]) =
      new MockRequestBuilder[T](responseBody, jsonParserTypeRef)
  }
}
