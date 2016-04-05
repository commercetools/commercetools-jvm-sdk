package io.sphere.sdk.taxcategories;

import java.math.BigDecimal;

/**
 * A SubRate is used to calculate the tax portions field in a cart or order.
 * It is useful if the total tax of a country is a combination of multiple taxes (e.g. state and local taxes)
 */
public interface SubRate {
    String getName();

    BigDecimal getAmount();

    static SubRate of(final String name, final BigDecimal amount) {
        return new SubRateImpl(name, amount);
    }
}
