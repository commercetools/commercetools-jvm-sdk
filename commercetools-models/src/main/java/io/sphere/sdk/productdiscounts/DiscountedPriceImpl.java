package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;

import javax.money.MonetaryAmount;

@JsonDeserialize(as = DiscountedPrice.class)
final class DiscountedPriceImpl extends Base implements DiscountedPrice {
    private final MonetaryAmount value;
    private final Reference<ProductDiscount> discount;

    @JsonCreator
    DiscountedPriceImpl(final MonetaryAmount value, final Reference<ProductDiscount> discount) {
        this.value = value;
        this.discount = discount;
    }

    public MonetaryAmount getValue() {
        return value;
    }

    public Reference<ProductDiscount> getDiscount() {
        return discount;
    }
}
