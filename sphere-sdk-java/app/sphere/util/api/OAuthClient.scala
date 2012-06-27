package sphere.util
package api

import play.api.mvc._
import play.mvc.Results

/** OAuth tokens returned by the authorization server. */
case class Tokens(accessToken: String, refreshToken: Option[String])

class OAuthClient(underlying: sphere.util.OAuthClient) extends Results {

  /** Asynchronously gets access and refresh tokens for given user from the Sphere authorization server
   * using the Resource owner credentials flow. */
  def getTokensForUser(
    tokenEndpoint: String, clientID: String, clientSecret: String, username: String, password: String,
    onError: ServiceError => Result,
    onSuccess: Tokens => Result): AsyncResult =
  {
    underlying.getTokensForUser(tokenEndpoint, clientID, clientSecret, username, password,
      playFunction(err => new JavaResult(onError(err))),
      playFunction(tokens => new JavaResult(onSuccess(Tokens(tokens.getAccessToken, Option(tokens.getRefreshToken)))))
    ).getWrappedResult.asInstanceOf[AsyncResult] // unwrap back Java -> Scala
  }

  /** Converts a Function1 to play.libs.F.Function. */
  private def playFunction[A, R](f: A => R) = new play.libs.F.Function[A, R] {
    def apply(a: A) = f(a)
  }

  /** Dummy Java result wrapping a Scala result. */
  private class JavaResult(val wrappedResult: play.api.mvc.Result) extends play.mvc.Result {
    def getWrappedResult: play.api.mvc.Result = wrappedResult
  }

//  def getTokenForCode() {
//    val err = p.get("error").map(_.head)
//        if (err.isDefined) {
//          renderError(p.get("error_description").map(_.head).getOrElse(err.get))
//        } else {
//        val code = p.get("code").map(_.head).getOrElse(renderError("The server did not return authorization code."))
//        val params = Map(
//          "grant_type" -> "authorization_code",
//          "redirect_uri" -> routes.Application.oauthCallback.absoluteURL(secure = false),
//          "code" -> code
//        )
//        val authHeader = Headers.encodeBasicAuthHeader(clientId, clientSecret)
//        val tokenEndpoint = "http://localhost:7777/oauth/token?" + Url.buildQueryString(params)
//        // POST params in request body not working
//        Async {
//          WS.url(tokenEndpoint).withHeaders("Authorization" -> authHeader).post("") map { resp =>
//            val jsonParser = new ObjectMapper
//            val json = jsonParser.readValue(resp.body, classOf[JsonNode])
//            val accessToken = json.path("access_token").getTextValue
//            val refreshToken = json.path("refresh_token").getTextValue
//            if (accessToken != null && refreshToken != null) {
//              Redirect(routes.Application.index).withSession("access_token" -> accessToken, "refresh_token" -> refreshToken)
//            } else {
//              renderError("The server did not return access token.")
//            }
//          }
//        }
//      }
//  }
}
