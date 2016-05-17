package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Reference;

import javax.money.MonetaryAmount;

@JsonDeserialize(as = DiscountedPriceImpl.class)
public interface DiscountedPrice {
    MonetaryAmount getValue();

    Reference<ProductDiscount> getDiscount();

    static DiscountedPrice of(final MonetaryAmount value, final Reference<ProductDiscount> discount) {
        return new DiscountedPriceImpl(value, discount);
    }
}
