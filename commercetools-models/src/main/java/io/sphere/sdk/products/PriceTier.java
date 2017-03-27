package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceValue;

import javax.money.MonetaryAmount;

/**
 * A tier price differs from the base price as is it used when a certain quantity of the {@link ProductVariant}
 * is added to a cart and ordered.
 * <p>
 * For example: the price can be lower if more than 10 items are ordered.
 * If no tier price is found for the order quantity, the base Price is used.
 *
 * @see PriceTierBuilder
 */
@ResourceValue
@HasBuilder(factoryMethods = @FactoryMethod(parameterNames = { "minimumQuantity", "value" }))
@JsonDeserialize(as = PriceTierImpl.class)
public interface PriceTier {
    /**
     * The minimum quantity this tier price is valid for.
     * The minimum quantity is always superior or equal to 2 (the base Price is interpreted as valid for minimum quantity equals to 1).
     *
     * @return the minimum quantity for this price tier
     */
    Integer getMinimumQuantity();

    /**
     * The currency of a tier price is always the same as the currency of the base {@link Price}.
     *
     * @return the value of this price tier
     */
    MonetaryAmount getValue();
}
