package io.sphere.sdk.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.javamoney.moneta.internal.DefaultRoundingProvider;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryRounding;
import javax.money.RoundingQueryBuilder;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

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
        final MonetaryRounding ROUNDING =
                ROUNDING_PROVIDER.getRounding(RoundingQueryBuilder.of().setRoundingName("default").setCurrency(monetaryAmount.getCurrency())
                .build());
        return monetaryAmount
                .with(ROUNDING)
                .query(MoneyRepresentation::queryFrom);
    }

    private static Long queryFrom(MonetaryAmount amount) {
        Objects.requireNonNull(amount, "Amount required.");
        BigDecimal number = amount.getNumber().numberValue(BigDecimal.class);
        CurrencyUnit cur = amount.getCurrency();
        int scale = cur.getDefaultFractionDigits();
        if(scale<0){
            scale = 0;
        }
        number = number.setScale(scale, RoundingMode.DOWN);
        return number.movePointRight(number.scale()).longValueExact();
    }
}
