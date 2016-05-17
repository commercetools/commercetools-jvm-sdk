package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.money.MonetaryAmount;

@JsonDeserialize(as = DiscountedLineItemPortionImpl.class)
public interface DiscountedLineItemPortion {
    static DiscountedLineItemPortion of(final Referenceable<CartDiscount> discount, final MonetaryAmount discountedAmount) {
        return new DiscountedLineItemPortionImpl(discount.toReference(), discountedAmount);
    }

    Reference<CartDiscount> getDiscount();

    MonetaryAmount getDiscountedAmount();
}
