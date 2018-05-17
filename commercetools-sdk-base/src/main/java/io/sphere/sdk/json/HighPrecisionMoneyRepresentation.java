package io.sphere.sdk.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.utils.HighPrecisionMoneyImpl;

import javax.money.MonetaryRounding;
import javax.money.RoundingQueryBuilder;
import java.math.BigDecimal;

final class HighPrecisionMoneyRepresentation extends MoneyRepresentation {

    private final BigDecimal preciseAmount;
    private final Integer fractionDigits;

    @JsonCreator
    public HighPrecisionMoneyRepresentation(final String currencyCode, final Long centAmount, final BigDecimal preciseAmount, final Integer fractionDigits) {
        super(centAmount, currencyCode);
        this.preciseAmount = preciseAmount;
        this.fractionDigits = fractionDigits;
    }

    /**
     * Creates a new Money instance.
     * Money can't represent cent fractions. The value will be rounded to nearest cent value using RoundingMode.HALF_EVEN.
     *
     * @param monetaryAmount the amount with currency to transform
     */
    @JsonIgnore
    public HighPrecisionMoneyRepresentation(final HighPrecisionMoneyImpl monetaryAmount) {
        super(amountToCents(monetaryAmount), requireValidCurrencyCode(monetaryAmount.getCurrency().getCurrencyCode()));
        preciseAmount = amountToPreciseAmount(monetaryAmount);
        fractionDigits = monetaryAmount.getFractionDigits();
    }

    public BigDecimal getPreciseAmount() {
        return preciseAmount;
    }

    public Integer getFractionDigits() {
        return fractionDigits;
    }

    private static BigDecimal amountToPreciseAmount(final HighPrecisionMoneyImpl monetaryAmount) {
        final MonetaryRounding ROUNDING =
                ROUNDING_PROVIDER.getRounding(RoundingQueryBuilder.of().setRoundingName("default").setCurrency(monetaryAmount.getCurrency())
                        .build());
        return monetaryAmount
                .with(ROUNDING)
                .query(amount -> queryFrom(monetaryAmount, monetaryAmount.getFractionDigits()));
    }


}
