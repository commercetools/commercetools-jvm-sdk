package io.sphere.sdk.models

import org.scalatest._
import java.math.BigDecimal
import com.fasterxml.jackson.databind.ObjectMapper
import com.neovisionaries.i18n.CurrencyCode

class MoneySpec extends WordSpec with ShouldMatchers {
  def eur(amount: Double) = new Money(new BigDecimal(amount), "EUR")

  "Money" must {
    "provide addition" in {
      eur(12.50).plus(eur(0.5)).getAmount.doubleValue should be(13.0)
      intercept[IllegalArgumentException] {
        eur(12.50).plus(new Money(new BigDecimal(0.50), "USD"))
      }
    }

    "provide multiplication" in {
      eur(15).multiply(0.5).getAmount.doubleValue should be(7.5)
      eur(15).multiply(1).getAmount.doubleValue should be(15)
      eur(15).multiply(2).getAmount.doubleValue should be(30)
      eur(15).multiply(0.19).getAmount.doubleValue should be(2.85)
      eur(15).multiply(0.118).getAmount.doubleValue should be(1.77)
      // 1 cents * 0.5 = 0.5 cents -> 0
      new Money(new BigDecimal("0.01"), "EUR").multiply(0.5).getAmount.doubleValue should be(0.00)
      // 3 cents * 0.5 = 1.5 cents -> 2
      new Money(new BigDecimal("0.03"), "EUR").multiply(0.5).getAmount.doubleValue should be(0.02)
      // 5 cents * 0.5 = 2.5 cents -> 2
      new Money(new BigDecimal("0.05"), "EUR").multiply(0.5).getAmount.doubleValue should be(0.02)
    }

    "contain the cent amount" in {
      eur(15).getAmount.toPlainString should be("15")
      eur(1.73).getAmount.toPlainString should be("1.73")
      eur(2.99).getAmount.toPlainString should be("2.99")
    }

    "round accordingly in construction" in {
      // RoundingMode.HALF_EVEN -> round towards even values
      new Money(new BigDecimal("12.595"), "EUR").getAmount.doubleValue should be(12.60)
      new Money(new BigDecimal("12.605"), "EUR").getAmount.doubleValue should be(12.60)
      new Money(new BigDecimal("12.615"), "EUR").getAmount.doubleValue should be(12.62)
      new Money(new BigDecimal("12.625"), "EUR").getAmount.doubleValue should be(12.62)
      new Money(new BigDecimal("2.999"), "EUR").getAmount.doubleValue should be(3.0)
    }

    "override toString" in {
      eur(12.6).toString should be("12.60 EUR")
      eur(0).toString should be("0.00 EUR")
      new Money(new BigDecimal(17.00), "USD").toString should be("17.00 USD")
      new Money(new BigDecimal(17.99), "CZK").toString should be("17.99 CZK")
    }

    "override equals" in {
      val a = eur(12.50)
      val a2 = eur(12.50)
      val b = eur(12.51)
      val c = eur(-12.50)
      a should equal (a)
      a should equal (a2)
      a should not equal (b)
      a should not equal (c)
      a should not equal (2)
    }

    "check for empty currencies" in {
      intercept[IllegalArgumentException](new Money(new BigDecimal(500), ""))
      intercept[IllegalArgumentException](new Money(new BigDecimal(500), null))
    }

    val moneyAsJsonString = """{"centAmount":4200,"currencyCode":"EUR"}"""
    val money = Money.fromCents(4200, "EUR")

    "be serializeable to JSON" in {
      new ObjectMapper().writeValueAsString(money) should be(moneyAsJsonString)
    }

    "be deserializable from JSON" in {
      new ObjectMapper().readValue(moneyAsJsonString, classOf[Money]) should be(money)
    }
  }
}

