package io.sphere.sdk.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.javamoney.moneta.function.MonetaryQueries;
import org.javamoney.moneta.internal.DefaultRoundingProvider;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryRounding;
import javax.money.RoundingQueryBuilder;

import static org.apache.commons.lang3.StringUtils.isEmpty;

final class MoneyRepresentation {
    private final Long centAmount;
    private final String currencyCode;

    private static final DefaultRoundingProvider ROUNDING_PROVIDER = new DefaultRoundingProvider();

    @JsonCreator
    private MoneyRepresentation(final Long centAmount, final String currencyCode) {
        this.centAmount = centAmount;
        this.currencyCode = currencyCode;
    }

    /**
     * Creates a new Money instance.
     * Money can't represent cent fractions. The value will be rounded to nearest cent value using RoundingMode.HALF_EVEN.
     * @param monetaryAmount the amount with currency to transform
     */
    @JsonIgnore
    public MoneyRepresentation(final MonetaryAmount monetaryAmount) {
        this(amountToCents(monetaryAmount), requireValidCurrencyCode(monetaryAmount.getCurrency().getCurrencyCode()));
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

    public static long amountToCents(final MonetaryAmount monetaryAmount) {
        final MonetaryRounding ROUNDING = ROUNDING_PROVIDER.getRounding(RoundingQueryBuilder.of().setRoundingName("default").setCurrency(monetaryAmount.getCurrency()).build());
        return monetaryAmount
                .with(ROUNDING)
                .query(MonetaryQueries.convertMinorPart());
    }
}
