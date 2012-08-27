package sphere

import org.scalatest.matchers.MustMatchers
import org.scalatest._
import com.ning.http.client.AsyncHttpClient
import util._
import de.commercetools.sphere.client.AuthorizationException
import com.google.common.base.Optional

class OAuthClientSpec extends WordSpec with MustMatchers {

  /** Fakes a request to authorization server by returning predefined response. */
  def getTokens(responseBody: String, statusCode: Int = 200): OAuthTokens = {
    val mockOAuthClient = new MockOAuthClient
    val dummyHttpClient = new AsyncHttpClient()
    val requestBuilder = dummyHttpClient.preparePost("http://sphere-token-endpoint")
    dummyHttpClient.close()
    mockOAuthClient.parseResponse(new MockHttpResponse(statusCode, responseBody), requestBuilder)
  }

  "Parse access_token" in {
    val tokens = getTokens("""{"access_token":"sometoken"}""")
    tokens.getAccessToken must be ("sometoken")
  }

  "Parse full" in {
    val tokens = getTokens("""{"access_token":"sometoken", "refresh_token":"refreshtoken", "expires_in":3600}""")
    tokens.getAccessToken must be ("sometoken")
    tokens.getRefreshToken must be ("refreshtoken")
    tokens.getExpiresIn must be (Optional.of(3600L))
  }

  "Fail on missing token" in {
    val e = intercept[IllegalArgumentException] {
      getTokens("""{"expires_in":3600}""")
    }
    e.getMessage must include ("must contain an access_token")
  }

  "Fail on malformed json" in {
    val e = intercept[RuntimeException] {
      getTokens("""{"access_token":}""")
    }
    e.getCause.isInstanceOf[org.codehaus.jackson.JsonParseException] must be (true)
  }

  "Report authorization error" in {
    val e = intercept[AuthorizationException] {
      getTokens("""{"error":"invalid_client"}""", 401)
    }
    e.getMessage must include ("""{"error":"invalid_client"}""")
    e.getMessage must include ("401")
  }
}
