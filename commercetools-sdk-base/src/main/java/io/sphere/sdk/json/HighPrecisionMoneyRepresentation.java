package io.sphere.sdk.json;

import com.fasterxml.jackson.annotation.JsonCreator;

final class HighPrecisionMoneyRepresentation extends MoneyRepresentation {

    private final Long preciseAmount;
    private final Long fractionDigits;

    @JsonCreator
    public HighPrecisionMoneyRepresentation(final String currencyCode, final Long centAmount, final Long preciseAmount, final Long fractionDigits) {
        super(centAmount, currencyCode);
        this.preciseAmount = preciseAmount;
        this.fractionDigits = fractionDigits;
    }


    public Long getPreciseAmount() {
        return preciseAmount;
    }

    public Long getFractionDigits() {
        return fractionDigits;
    }
}
