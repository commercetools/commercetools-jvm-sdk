package sphere.util

import play.api.libs.ws.WS
import play.api.mvc._
import play.api.mvc.Results.Async
import play.api.mvc.Results.Status

object Http extends Results {
  /**Proxies a request containing a JSON command payload to given url. */
  def proxyCommand(req: Request[AnyContent], proxyUrl: String): Result = {
    req.body.asJson.map(jsonCommand =>
      Async {
        WS.url(proxyUrl).post(jsonCommand) map {
          resp =>
            new Status(resp.status)(resp.body)
        }
      }
    ).getOrElse(BadRequest("JSON command expected."))
  }

  /**Proxies a get request to given url. */
  def proxyGet(req: Request[AnyContent], proxyUrl: String): Result = {
    Async {
      WS.url(proxyUrl).withHeaders(req.headers.toSimpleMap.toList: _*).get map {
        resp =>
          new Status(resp.status)(resp.body)
      }
    }
  }
}
