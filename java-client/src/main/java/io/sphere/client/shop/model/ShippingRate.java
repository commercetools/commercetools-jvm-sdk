package io.sphere.client.shop.model;

import javax.annotation.Nonnull;
import io.sphere.client.model.Money;

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

    /** Shipping is free when the cart total is above the amount. */
    public Money getFreeAbove() { return freeAbove; }
}
