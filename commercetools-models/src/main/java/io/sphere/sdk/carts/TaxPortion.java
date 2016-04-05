package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

public final class TaxPortion extends Base {
    private final Double rate;
    private final MonetaryAmount amount;
    @Nullable
    private final String name;

    @JsonCreator
    private TaxPortion(final Double rate, final MonetaryAmount amount, final String name) {
        this.rate = rate;
        this.amount = amount;
        this.name = name;
    }

    public static TaxPortion of(final double rate, final MonetaryAmount amount) {
        return new TaxPortion(rate, amount, null);
    }

    public static TaxPortion of(final double rate, final MonetaryAmount amount, final String name) {
        return new TaxPortion(rate, amount, name);
    }

    public Double getRate() {
        return rate;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    @Nullable
    public String getName() {
        return name;
    }
}
