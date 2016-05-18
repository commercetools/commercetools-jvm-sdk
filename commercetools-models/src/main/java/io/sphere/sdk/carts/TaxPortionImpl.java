package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

final class TaxPortionImpl extends Base implements TaxPortion {
    private final Double rate;
    private final MonetaryAmount amount;
    @Nullable
    private final String name;

    @JsonCreator
    TaxPortionImpl(final Double rate, final MonetaryAmount amount, final String name) {
        this.rate = rate;
        this.amount = amount;
        this.name = name;
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
