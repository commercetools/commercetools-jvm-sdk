import com.fasterxml.jackson.core.`type`.TypeReference
import io.sphere.sdk.client.{HttpMethod, HttpRequest, FetchImpl, Fetch}

class XyzService {
  def fetchById(id: String): Fetch[Xyz] = new FetchImpl[Xyz] {
    override protected def typeReference() = new TypeReference[Xyz]() {}

    override def httpRequest() = HttpRequest.of(HttpMethod.GET, "/")
  }
}