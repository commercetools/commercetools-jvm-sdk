import com.fasterxml.jackson.core.`type`.TypeReference
import io.sphere.sdk.models.Versioned
import io.sphere.sdk.queries.{FetchImpl, Fetch}
import io.sphere.sdk.http.{JsonEndpoint, HttpRequest, HttpMethod}

class XyzService {
  def fetchById(id: String): Fetch[Xyz] = new FetchImpl[Xyz](Versioned.of(id, 0), JsonEndpoint.of(new TypeReference[Xyz]() {}, "/dummy-endpoint")) {
  }
}