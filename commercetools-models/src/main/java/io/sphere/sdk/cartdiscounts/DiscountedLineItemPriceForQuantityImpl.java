package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

final class DiscountedLineItemPriceForQuantityImpl extends Base implements DiscountedLineItemPriceForQuantity {
    private final Long quantity;
    private final DiscountedLineItemPrice discountedPrice;

    @JsonCreator
    DiscountedLineItemPriceForQuantityImpl(final DiscountedLineItemPrice discountedPrice, final Long quantity) {
        this.discountedPrice = discountedPrice;
        this.quantity = quantity;
    }

    public DiscountedLineItemPrice getDiscountedPrice() {
        return discountedPrice;
    }

    public Long getQuantity() {
        return quantity;
    }
}
