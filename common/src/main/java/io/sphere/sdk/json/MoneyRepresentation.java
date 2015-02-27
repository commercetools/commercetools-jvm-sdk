package io.sphere.sdk.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.apache.commons.lang3.StringUtils.isEmpty;

final class MoneyRepresentation {
    private final long centAmount;
    private final String currencyCode;

    @JsonCreator
    private MoneyRepresentation(final long centAmount, final String currencyCode) {
        this.centAmount = centAmount;
        this.currencyCode = currencyCode;
    }

    /**
     * Creates a new Money instance.
     * Money can't represent cent fractions. The value will be rounded to nearest cent value using RoundingMode.HALF_EVEN.
     * @param amount the money value as fraction, e.g. 43.21 will be 4321 cents.
     * @param currencyCode the ISO 4217 currency code
     */
    @JsonIgnore
    public MoneyRepresentation(final BigDecimal amount, final String currencyCode) {
        this(amountToCents(amount), requireValidCurrencyCode(currencyCode));
    }

    public long getCentAmount() {
        return centAmount;
    }

    /**
     * @return The ISO 4217 currency code, for example "EUR" or "USD".
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    private static String requireValidCurrencyCode(final String currencyCode) {
        if (isEmpty(currencyCode))
            throw new IllegalArgumentException("Money.currencyCode can't be empty.");
        return currencyCode;
    }

    public static long amountToCents(final BigDecimal centAmount) {
        return centAmount.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_EVEN).longValue();
    }
}
