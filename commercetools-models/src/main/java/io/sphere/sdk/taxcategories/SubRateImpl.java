package io.sphere.sdk.taxcategories;

final class SubRateImpl implements SubRate {
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
