package io.sphere.sdk.taxcategories;

import io.sphere.sdk.models.Base;

final class SubRateImpl extends Base implements SubRate {
    private final String name;
    private final Double amount;

    SubRateImpl(final String name, final Double amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public Double getAmount() {
        return amount;
    }

    @Override
    public String getName() {
        return name;
    }
}
