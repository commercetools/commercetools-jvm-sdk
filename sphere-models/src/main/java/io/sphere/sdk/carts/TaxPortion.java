package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.money.MonetaryAmount;

public final class TaxPortion extends Base {
    private final Double rate;
    private final MonetaryAmount amount;

    @JsonCreator
    private TaxPortion(final Double rate, final MonetaryAmount amount) {
        this.rate = rate;
        this.amount = amount;
    }

    public static TaxPortion of(final double rate, final MonetaryAmount amount) {
        return new TaxPortion(rate, amount);
    }

    public Double getRate() {
        return rate;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }
}
