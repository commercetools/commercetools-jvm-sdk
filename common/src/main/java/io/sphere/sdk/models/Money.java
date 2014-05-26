package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import net.jcip.annotations.Immutable;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Money represented by amount and currency.
 * <p/>
 * The precision is whole cents. Fractional cents can't be represented and amounts
 * will always be rounded to nearest cent value when performing calculations.
 */
@Immutable
public class Money {
    private final long centAmount;
    private final String currencyCode;

    @JsonCreator
    private Money(@JsonProperty("centAmount") final long centAmount,
                  @JsonProperty("currencyCode") final String currencyCode) {
        this.centAmount = centAmount;
        this.currencyCode = currencyCode;
    }

    /**
     * Creates a new Money instance.
     * Money can't represent cent fractions. The value will be rounded to nearest cent value using RoundingMode.HALF_EVEN.
     * @param amount the money value as fraction, e.g. 43.21 will be 4321 cents.
     */
    public Money(final BigDecimal amount, final String currencyCode) {
        this(amountToCents(amount), requireValidCurrencyCode(currencyCode));
    }

    public static Money fromCents(final long centAmount, final String currencyCode) {
        return new Money(centAmount, currencyCode);
    }

    /**
     * The exact amount as BigDecimal, useful for implementing e.g. custom rounding / formatting methods.
     */
    @JsonIgnore
    public BigDecimal getAmount() {
        return centsToAmount(centAmount);
    }

    /**
     * The cent amount.
     */
    public long getCentAmount() {
        return centAmount;
    }

    /**
     * The ISO 4217 currency code, for example "EUR" or "USD".
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    private static String requireValidCurrencyCode(final String currencyCode) {
        if (Strings.isNullOrEmpty(currencyCode))
            throw new IllegalArgumentException("Money.currencyCode can't be empty.");
        return currencyCode;
    }

    /**
     * Returns a new Money instance that is a sum of this instance and given instance.
     */
    public Money plus(final Money amount) {
        if (!amount.currencyCode.equals(this.currencyCode)) {
            throw new IllegalArgumentException(String.format("Can't add Money instances of different currencies: %s, %s", this, amount));
        }
        return new Money(centAmount + amount.centAmount, currencyCode);
    }

    /**
     * Returns a new Money instance that has the amount multiplied by given factor.
     * Rounding may be necessary to round fractional cents to the nearest cent value.
     */
    public Money multiply(final double multiplier, final RoundingMode roundingMode) {
        long newCentAmount = new BigDecimal(centAmount).multiply(new BigDecimal(multiplier)).setScale(0, roundingMode).longValue();
        return new Money(newCentAmount, currencyCode);
    }

    /**
     * Returns a new Money instance that has the amount multiplied by given factor.
     * Fractional cents will be rounded to the nearest cent value using Banker's rounding algorithm (RoundingMode.HALF_EVEN).
     */
    public Money multiply(final double multiplier) {
        return multiply(multiplier, RoundingMode.HALF_EVEN);
    }

    /**
     * Formats the amount to given number of decimal places.
     * <p/>
     * Example:
     * {@code price.format(2) => "3.50"}
     */
    public String format(final int decimalPlaces) {
        return getAmount().setScale(decimalPlaces).toPlainString();
    }

    @Override
    public String toString() {
        return format(2) + " " + this.currencyCode;
    }

    public static long amountToCents(final BigDecimal centAmount) {
        return centAmount.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_EVEN).longValue();
    }

    public static BigDecimal centsToAmount(final long centAmount) {
        return new BigDecimal(centAmount).divide(new BigDecimal(100));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        if (centAmount != money.centAmount) return false;
        if (!currencyCode.equals(money.currencyCode)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = currencyCode.hashCode();
        result = 31 * result + (int) (centAmount ^ (centAmount >>> 32));
        return result;
    }
}
