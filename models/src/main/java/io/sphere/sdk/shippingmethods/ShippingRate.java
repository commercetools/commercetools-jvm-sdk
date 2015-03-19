package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.sphere.sdk.models.Base;

import javax.money.MonetaryAmount;
import java.util.Optional;

public class ShippingRate extends Base {
    private final MonetaryAmount price;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Optional<MonetaryAmount> freeAbove;

    @JsonCreator
    private ShippingRate(final MonetaryAmount price, final Optional<MonetaryAmount> freeAbove) {
        this.price = price;
        this.freeAbove = freeAbove;
    }

    public static ShippingRate of(final MonetaryAmount price, final MonetaryAmount freeAbove) {
        return of(price, Optional.of(freeAbove));
    }

    public static ShippingRate of(final MonetaryAmount price, final Optional<MonetaryAmount> freeAbove) {
        return new ShippingRate(price, freeAbove);
    }

    public static ShippingRate of(final MonetaryAmount price) {
        return of(price, Optional.empty());
    }

    public MonetaryAmount getPrice() {
        return price;
    }

    public Optional<MonetaryAmount> getFreeAbove() {
        return freeAbove;
    }
}
