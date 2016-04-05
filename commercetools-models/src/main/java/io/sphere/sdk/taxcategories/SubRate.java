package io.sphere.sdk.taxcategories;

/**
 * A SubRate is used to calculate the tax portions field in a cart or order.
 * It is useful if the total tax of a country is a combination of multiple taxes (e.g. state and local taxes)
 */
public interface SubRate {
    String getName();

    Double getAmount();

    static SubRate of(final String name, final Double amount) {
        return new SubRateImpl(name, amount);
    }
}
