package io.sphere.client.shop.model;

import javax.annotation.Nonnull;
import io.sphere.client.model.Money;

/** A shipping rate defines the cost of shipping an order. */
public class ShippingRate {
    @Nonnull private Money price;
    private Money freeAbove;

    // for JSON deserializer
    private ShippingRate() {}

    public ShippingRate(@Nonnull Money price) { this.price = price; }

    public ShippingRate(@Nonnull Money price, Money freeAbove) {
        this.price = price;
        this.freeAbove = freeAbove;
    }

    /** The price of the shipping. */
    @Nonnull public Money getPrice() { return price; }

    /** The shipping is free if the order total (the sum of line item prices) exceeds the freeAbove value. */
    public Money getFreeAbove() { return freeAbove; }
}
