import com.fasterxml.jackson.core.`type`.TypeReference
import io.sphere.client.shop.{SphereClient, SphereClientConfig}
import io.sphere.sdk.client._
import java.util.Locale
import org.scalatest._
import com.typesafe.config.ConfigFactory
import scala.util.Properties._
import scala.collection.JavaConversions._

class SphereJavaClientIntegrationSpec extends WordSpec with ShouldMatchers {

  object IntegrationTestClient {
    def apply() = {
      val Seq(projectKey,clientId,clientSecret) = getConfiguration
      val builder = new SphereClientConfig.Builder(projectKey, clientId, clientSecret, Locale.ENGLISH)
      envOrNone("SDK_IT_SERVICE_URL").map(builder.setCoreHttpServiceUrl(_))
      envOrNone("SDK_IT_AUTH_URL").map(builder.setAuthHttpServiceUrl(_))
      SphereClient.create(builder.build)
    }

    def getConfiguration: Seq[String] = {
      val values = Seq("SDK_IT_PROJECT_KEY", "SDK_IT_CLIENT_ID", "SDK_IT_CLIENT_SECRET").map(key => (key, envOrNone(key)))
      val missingValues = values.filterNot(_._2.isDefined)
      if (!missingValues.isEmpty) {
        throw new RuntimeException(s"Missing environment arguments: ${missingValues.map(_._1).mkString(",")}")
      }
      val Seq(projectKey, clientId, clientSecret) = values.map(_._2.get)
      Seq(projectKey, clientId, clientSecret)
    }
  }


  val config = {
    for {
      core <- envOrNone("SDK_IT_SERVICE_URL")
      auth <- envOrNone("SDK_IT_AUTH_URL")
      key <- envOrNone("SDK_IT_PROJECT_KEY")
      id <- envOrNone("SDK_IT_CLIENT_ID")
      secret <- envOrNone("SDK_IT_CLIENT_SECRET")
    } yield {
      val map = Map(
        "sphere.core" -> core,
        "sphere.auth" -> auth,
        "sphere.project" -> key,
        "sphere.clientId" -> id,
        "sphere.clientSecret" -> secret
      )
      ConfigFactory.parseMap(map).withFallback(ConfigFactory.load())
    }
  }.get

  "SPHERE.IO Java client" must {
    "authenticate" in {
      val client: SphereJavaClient = new SphereJavaClientImpl(config)
      client.execute(new Fetch[String](new TypeReference[String] {}) {
        override def httpRequest(): HttpRequest = HttpRequest.of(HttpMethod.GET, "/categories")
      })
    }
  }
}