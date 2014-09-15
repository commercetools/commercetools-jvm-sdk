package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Money;
import io.sphere.sdk.models.Reference;

public class DiscountedPrice extends Base {
    private final Money value;
    private final Reference<ProductDiscount> discount;

    @JsonCreator
    DiscountedPrice(final Money value, final Reference<ProductDiscount> discount) {
        this.value = value;
        this.discount = discount;
    }

    public Money getValue() {
        return value;
    }

    public Reference<ProductDiscount> getDiscount() {
        return discount;
    }

    public static DiscountedPrice of(final Money value, final Reference<ProductDiscount> discount) {
        return new DiscountedPrice(value, discount);
    }
}
