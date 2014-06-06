import com.fasterxml.jackson.core.`type`.TypeReference
import io.sphere.sdk.client.{HttpMethod, Fetch, HttpRequest}

class XyzService {
  def fetchById(id: String): Fetch[Xyz, Xyz] = new Fetch[Xyz, Xyz](new TypeReference[Xyz]() {}) {
    override def httpRequest(): HttpRequest = HttpRequest.of(HttpMethod.GET, "/")
  }
}