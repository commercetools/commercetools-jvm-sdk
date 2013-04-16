package io.sphere.client.shop.model;

import io.sphere.client.model.Money;

/** Represents the tax portion amount of a specific tax rate in a {@link TaxedPrice}.
 * <p>
 * For example, the gross price of 210 EUR at tax rate 5% has the tax portion with the rate 0.05 and amount 10EUR.
 * */
public class TaxPortion {
    private Double rate;
    private Money amount;

    // for JSON deserializer
    private TaxPortion() {}

    /** The tax rate of this portion. */
    public Double getRate() { return rate; }

    /** The amount of the tax portion for the specific rate. */
    public Money getAmount() { return amount; }
}
