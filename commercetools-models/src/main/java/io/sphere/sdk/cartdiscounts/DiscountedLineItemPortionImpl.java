package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;

import javax.money.MonetaryAmount;

final class DiscountedLineItemPortionImpl extends Base implements DiscountedLineItemPortion {
    private final Reference<CartDiscount> discount;
    private final MonetaryAmount discountedAmount;

    @JsonCreator
    DiscountedLineItemPortionImpl(final Reference<CartDiscount> discount, final MonetaryAmount discountedAmount) {
        this.discount = discount;
        this.discountedAmount = discountedAmount;
    }

    public Reference<CartDiscount> getDiscount() {
        return discount;
    }

    public MonetaryAmount getDiscountedAmount() {
        return discountedAmount;
    }
}
