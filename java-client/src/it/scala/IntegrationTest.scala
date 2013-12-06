package sphere

import com.google.common.collect.ImmutableMap
import io.sphere.client.model.{Money, LocalizedString}
import java.util.{UUID, Currency, Locale}
import org.scalatest.matchers.{MatchResult, Matcher}

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

  object beSimilar {
    def apply[T](right: T, keyFn: (T => Any)*) =  new Matcher[T] {
      def apply(left: T): MatchResult = {
        val results: Seq[MatchResult] = keyFn.map(fn => (fn(left), fn(right))).map {
          case (a, b) =>
            new MatchResult(a == b, s"$a did not equal $b", s"$a equaled $b")
        }
        val errors = results.filterNot(_.matches)
        if(errors.size > 0) errors(0)
        else new MatchResult(true, s"$left did not equal $right", s"$left equaled $right")
      }
    }
  }
}
