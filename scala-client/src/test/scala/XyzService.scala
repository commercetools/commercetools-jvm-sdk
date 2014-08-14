import com.fasterxml.jackson.core.`type`.TypeReference
import io.sphere.sdk.models.Versioned
import io.sphere.sdk.queries.{FetchImpl, Fetch}
import io.sphere.sdk.requests.HttpRequest
import io.sphere.sdk.requests.{HttpRequest, HttpMethod}

class XyzService {
  def fetchById(id: String): Fetch[Xyz] = new FetchImpl[Xyz](Versioned.of(id, 0)) {
    override protected def typeReference() = new TypeReference[Xyz]() {}

    override protected def baseEndpointWithoutId(): String = "/dummy-endpoint"
  }
}