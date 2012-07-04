package sphere

import org.scalatest.matchers.MustMatchers
import sphere.Mocks.MockOAuthClient
import play.libs.F
import util._
import org.scalatest.{Tag, WordSpec}

class OAuthClientSpec extends WordSpec with MustMatchers {

  /** Fakes a request to authorization server by returning predefined response. */
  def testRequest(requestBody: String, status: Int = 200)(assert: Validation[Tokens] => Any) = {
    val oauthClient = new MockOAuthClient(status = status, body = requestBody)
    oauthClient.getTokensForClient("http://sphere-token-endpoint", "clientId", "clientSecret", "scope",
      new F.Function[ServiceError, Validation[Tokens]] {
        override def apply(err: ServiceError): Validation[Tokens] = {
          val v = Validation.failure[Tokens](err).asInstanceOf[Validation[Tokens]]
          assert(v); v
        }
      },
      new F.Function[Tokens, Validation[Tokens]] {
        override def apply(tokens: Tokens): Validation[Tokens] = {
          val v: Validation[Tokens] = Validation.success[Tokens](tokens).asInstanceOf[Validation[Tokens]]
          assert(v); v
        }
      }
    ).get();
  }

  "Parse access_token" in {
    testRequest("""{"access_token":"sometoken"}""")(tokens => {
      tokens.isError must be(false)
      tokens.getValue.getAccessToken must be ("sometoken")
      tokens.getValue.getExpiresIn must be (-1)
    })
  }

  "Parse expires_in" in {
    testRequest("""{"access_token":"sometoken", "expires_in":3600}""")(tokens => {
      tokens.isError must be(false)
      tokens.getValue.getAccessToken must be ("sometoken")
      tokens.getValue.getExpiresIn must be (3600)
    })
  }

  "Fail on missing token" in {
    testRequest("""{"expires_in":3600}""")(tokens => {
      tokens.isError must be (true)
      tokens.getError.getMessage must startWith ("Authorization server did not return access token")
    })
  }

  "Fail on malformed json" ignore {
    testRequest("""{"expires:3600}""")(tokens => {
      tokens.isError must be (true)
      tokens.getError.getMessage must be ("Authorization server did not return access token.")
    })
  }

  "Report authorization error" in {
    testRequest("""{"error":"invalid_client"}""", 401)(tokens => {
      tokens.isError must be(true)
      tokens.getError.getErrorType must be(ServiceErrorType.AuthorizationError)
    })
  }
}
