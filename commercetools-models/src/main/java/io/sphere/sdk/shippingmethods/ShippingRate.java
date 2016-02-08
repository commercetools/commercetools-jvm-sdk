package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

public final class ShippingRate extends Base {
    private final MonetaryAmount price;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nullable
    private final MonetaryAmount freeAbove;

    @JsonCreator
    private ShippingRate(final MonetaryAmount price, @Nullable final MonetaryAmount freeAbove) {
        this.price = price;
        this.freeAbove = freeAbove;
    }

    public static ShippingRate of(final MonetaryAmount price, @Nullable final MonetaryAmount freeAbove) {
        return new ShippingRate(price, freeAbove);
    }

    public static ShippingRate of(final MonetaryAmount price) {
        return of(price, null);
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    @Nullable
    public MonetaryAmount getFreeAbove() {
        return freeAbove;
    }
}
