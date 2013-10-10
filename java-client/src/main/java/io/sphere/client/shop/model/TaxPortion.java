package io.sphere.client.shop.model;

import io.sphere.client.model.Money;

import javax.annotation.Nonnull;

/** Represents the tax portion amount of a specific tax rate in a {@link TaxedPrice}.
 *
 * <p>For example, a 5% tax for gross price of 200 EUR is represented by a TaxPortion(0.05, 10 EUR). */
public class TaxPortion {
    private double rate;
    @Nonnull private Money amount;

    // for JSON deserializer
    private TaxPortion() {}

    /** The tax rate of this portion. */
    public double getRate() { return rate; }

    /** The absolute amount this tax portion evaluates to. */
    @Nonnull public Money getAmount() { return amount; }

    @Override
    public String toString() {
        return "TaxPortion{" +
                "rate=" + rate +
                ", amount=" + amount +
                '}';
    }
}
