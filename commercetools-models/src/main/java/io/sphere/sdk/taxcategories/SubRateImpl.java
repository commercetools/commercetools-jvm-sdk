package io.sphere.sdk.taxcategories;

import java.math.BigDecimal;

final class SubRateImpl implements SubRate {
    private final String name;
    private final BigDecimal amount;

    SubRateImpl(final String name, final BigDecimal amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String getName() {
        return name;
    }
}
