package sphere

import com.google.common.collect.ImmutableMap
import io.sphere.client.model.{Money, LocalizedString}
import io.sphere.client.shop.{SphereClient, SphereClientConfig}
import java.util.{Currency, Locale}
import scala.util.Properties._

object IntegrationTest {
  object Implicits {
    implicit lazy val EUR = Currency.getInstance("EUR")
    implicit def string2localizedString(s: String): LocalizedString =
      new LocalizedString(ImmutableMap.of(Locale.ENGLISH, s, Locale.FRENCH, s"le ${s}"))

    implicit val locale = Locale.ENGLISH

    implicit final class MoneyNotation(val currency: Currency) extends AnyVal {
      def apply(amount: String): Money = new Money(new java.math.BigDecimal(amount), currency.getCurrencyCode)
    }
  }
}

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