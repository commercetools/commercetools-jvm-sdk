package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;

import javax.money.MonetaryAmount;

public final class DiscountedPrice extends Base {
    private final MonetaryAmount value;
    private final Reference<ProductDiscount> discount;

    @JsonCreator
    DiscountedPrice(final MonetaryAmount value, final Reference<ProductDiscount> discount) {
        this.value = value;
        this.discount = discount;
    }

    public MonetaryAmount getValue() {
        return value;
    }

    public Reference<ProductDiscount> getDiscount() {
        return discount;
    }

    public static DiscountedPrice of(final MonetaryAmount value, final Reference<ProductDiscount> discount) {
        return new DiscountedPrice(value, discount);
    }
}
