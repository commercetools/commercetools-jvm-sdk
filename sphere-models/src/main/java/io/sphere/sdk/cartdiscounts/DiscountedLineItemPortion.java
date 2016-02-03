package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.money.MonetaryAmount;

public final class DiscountedLineItemPortion extends Base {
    private final Reference<CartDiscount> discount;
    private final MonetaryAmount discountedAmount;

    @JsonCreator
    private DiscountedLineItemPortion(final Reference<CartDiscount> discount, final MonetaryAmount discountedAmount) {
        this.discount = discount;
        this.discountedAmount = discountedAmount;
    }

    public static DiscountedLineItemPortion of(final Referenceable<CartDiscount> discount, final MonetaryAmount discountedAmount) {
        return new DiscountedLineItemPortion(discount.toReference(), discountedAmount);
    }

    public Reference<CartDiscount> getDiscount() {
        return discount;
    }

    public MonetaryAmount getDiscountedAmount() {
        return discountedAmount;
    }
}
