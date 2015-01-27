package io.sphere.sdk.carts;

import io.sphere.sdk.models.Base;

import javax.money.MonetaryAmount;

public class TaxPortion extends Base {
    private final double rate;
    private final MonetaryAmount amount;

    private TaxPortion(final double rate, final MonetaryAmount amount) {
        this.rate = rate;
        this.amount = amount;
    }

    public static TaxPortion of(final double rate, final MonetaryAmount amount) {
        return new TaxPortion(rate, amount);
    }

    public double getRate() {
        return rate;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }
}
