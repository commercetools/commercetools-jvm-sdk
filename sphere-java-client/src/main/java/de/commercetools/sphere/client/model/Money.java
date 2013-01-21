package de.commercetools.sphere.client.model;

import net.jcip.annotations.*;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Immutable
public class Money {
    private final long centAmount;
    private final String currencyCode;

    // JSON constructor (to keep fields final)
    @JsonCreator public Money(@JsonProperty("centAmount") long centAmount, @JsonProperty("currencyCode") String currencyCode) {
        this.centAmount = centAmount;
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
        long newCentAmount = getAmount().multiply(new BigDecimal(multiplier)).setScale(0, roundingMode).longValue();
        return new Money(newCentAmount, currencyCode);
    }

    /** Returns a new Money instance that has the amount multiplied by given factor.
     *  Fractional cents will be rounded to the nearest cent value using Banker's rounding algorithm (RoundingMode.HALF_EVEN). */
    public Money multiply(double multiplier) {
        return multiply(multiplier, RoundingMode.HALF_EVEN);
    }

    /** The ISO 4217 currency code, for example "EUR" or "USD". */
    public String getCurrencyCode() { return currencyCode; }

    /** The exact amount as BigDecimal, useful for implementing e.g. custom rounding / formatting methods. */
    public BigDecimal getAmount() { return new BigDecimal(centAmount).divide(new BigDecimal(100));}

    @Override public String toString() {
        return "[" + getAmount() + " " + this.currencyCode + "]";
    }
}