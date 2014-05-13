package io.sphere.client.model

import org.scalatest._

class MoneySpec extends WordSpec with MustMatchers {
  def eur(amount: Double) = new Money(new java.math.BigDecimal(amount), "EUR")

  "Money.plus" in {
    eur(12.50).plus(eur(0.5)).getAmount.doubleValue must be (13.0)
    intercept[IllegalArgumentException] {
      eur(12.50).plus(new Money(new java.math.BigDecimal(0.50), "USD"))
    }
  }

  "Money.multiply" in {
    eur(15).multiply(0.5).getAmount.doubleValue must be (7.5)
    eur(15).multiply(1).getAmount.doubleValue must be (15)
    eur(15).multiply(2).getAmount.doubleValue must be (30)
    eur(15).multiply(0.19).getAmount.doubleValue must be (2.85)
    eur(15).multiply(0.118).getAmount.doubleValue must be (1.77)
    // 1 cents * 0.5 = 0.5 cents -> 0
    new Money(new java.math.BigDecimal("0.01"), "EUR").multiply(0.5).getAmount.doubleValue must be (0.00)
    // 3 cents * 0.5 = 1.5 cents -> 2
    new Money(new java.math.BigDecimal("0.03"), "EUR").multiply(0.5).getAmount.doubleValue must be (0.02)
    // 5 cents * 0.5 = 2.5 cents -> 2
    new Money(new java.math.BigDecimal("0.05"), "EUR").multiply(0.5).getAmount.doubleValue must be (0.02)
  }

  "Money.getAmount" in {
    eur(15).getAmount.toPlainString must be ("15")
    eur(1.73).getAmount.toPlainString must be ("1.73")
    eur(2.99).getAmount.toPlainString must be ("2.99")
  }

  "Money ctor rounding" in {
    // RoundingMode.HALF_EVEN -> round towards even values
    new Money(new java.math.BigDecimal("12.595"), "EUR").getAmount.doubleValue must be (12.60)
    new Money(new java.math.BigDecimal("12.605"), "EUR").getAmount.doubleValue must be (12.60)
    new Money(new java.math.BigDecimal("12.615"), "EUR").getAmount.doubleValue must be (12.62)
    new Money(new java.math.BigDecimal("12.625"), "EUR").getAmount.doubleValue must be (12.62)
    new Money(new java.math.BigDecimal("2.999"), "EUR").getAmount.doubleValue must be (3.0)
  }

  "Money.toString" in {
    eur(12.6).toString must be ("12.60 EUR")
    eur(0).toString must be ("0.00 EUR")
    new Money(new java.math.BigDecimal(17.00), "USD").toString must be ("17.00 USD")
    new Money(new java.math.BigDecimal(17.99), "CZK").toString must be ("17.99 CZK")
  }
}
