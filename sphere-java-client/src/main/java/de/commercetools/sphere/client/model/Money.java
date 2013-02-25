package de.commercetools.sphere.client.model;

import net.jcip.annotations.*;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/** Money represented by amount and currency.
 *
 * The precision is whole cents. Fractional cents can't be represented and amounts
 * will always be rounded to nearest cent value when performing calculations. */
@Immutable
public class Money {
    private final String currencyCode;
    private final long centAmount;

    /** The ISO 4217 currency code, for example "EUR" or "USD". */
    public String getCurrencyCode() { return currencyCode; }

    /** The exact amount as BigDecimal, useful for implementing e.g. custom rounding / formatting methods. */
    public BigDecimal getAmount() { return centsToAmount(centAmount); }

    @JsonCreator private Money(@JsonProperty("centAmount") long centAmount, @JsonProperty("currencyCode") String currencyCode) {
        this.centAmount = centAmount;
        this.currencyCode = currencyCode;
    }

    public static long amountToCents(BigDecimal centAmount) {
        return centAmount.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_EVEN).longValue();
    }

    public static BigDecimal centsToAmount(long centAmount) {
        return new BigDecimal(centAmount).divide(new BigDecimal(100));
    }

    public static BigDecimal centsToAmount(double centAmount) {
        return new BigDecimal(centAmount).divide(new BigDecimal(100));
    }

    /** Creates a new Money instance.
     * Money can't represent cent fractions. The value will be rounded to nearest cent value using RoundingMode.HALF_EVEN. */
    public Money(BigDecimal amount, String currencyCode) {
        this.centAmount = amountToCents(amount);
        this.currencyCode = currencyCode;
    }

    /** Returns a new Money instance that is a sum of this instance and given instance. */
    public Money plus(Money amount) {
        if (!amount.currencyCode.equals(this.currencyCode)) {
            throw new IllegalArgumentException(String.format("Can't add Money instances of different currencies: %s, %s", this, amount));
        }
        return new Money(centAmount + amount.centAmount, currencyCode);
    }

    /** Returns a new Money instance that has the amount multiplied by given factor.
     *  Rounding may be necessary to round fractional cents to the nearest cent value. */
    public Money multiply(double multiplier, RoundingMode roundingMode) {
        long newCentAmount = new BigDecimal(centAmount).multiply(new BigDecimal(multiplier)).setScale(0, roundingMode).longValue();
        return new Money(newCentAmount, currencyCode);
    }

    /** Returns a new Money instance that has the amount multiplied by given factor.
     *  Fractional cents will be rounded to the nearest cent value using Banker's rounding algorithm (RoundingMode.HALF_EVEN). */
    public Money multiply(double multiplier) {
        return multiply(multiplier, RoundingMode.HALF_EVEN);
    }

    @Override public String toString() {
        return getAmount().setScale(2).toPlainString() + " " + this.currencyCode;
    }

    // ---------------------------------
    // equals() and hashCode()
    // ---------------------------------

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        if (centAmount != money.centAmount) return false;
        if (!currencyCode.equals(money.currencyCode)) return false;
        return true;
    }

    @Override public int hashCode() {
        int result = currencyCode.hashCode();
        result = 31 * result + (int) (centAmount ^ (centAmount >>> 32));
        return result;
    }
}