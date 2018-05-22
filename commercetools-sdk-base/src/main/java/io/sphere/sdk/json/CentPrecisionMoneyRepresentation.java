package io.sphere.sdk.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.money.MonetaryAmount;

final class CentPrecisionMoneyRepresentation extends MoneyRepresentation{


    @JsonCreator
    public CentPrecisionMoneyRepresentation(final Long centAmount,final String currencyCode) {
        super(centAmount, currencyCode);
    }

    /**
     * Creates a new Money instance.
     * Money can't represent cent fractions. The value will be rounded to nearest cent value using RoundingMode.HALF_EVEN.
     * @param monetaryAmount the amount with currency to transform
     */
    @JsonIgnore
    public CentPrecisionMoneyRepresentation(final MonetaryAmount monetaryAmount) {
        super(amountToCents(monetaryAmount), requireValidCurrencyCode(monetaryAmount.getCurrency().getCurrencyCode()));
    }

}
