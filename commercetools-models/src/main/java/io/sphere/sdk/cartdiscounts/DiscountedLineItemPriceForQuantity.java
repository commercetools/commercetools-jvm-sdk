package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public final class DiscountedLineItemPriceForQuantity extends Base {
    private final Long quantity;
    private final DiscountedLineItemPrice discountedPrice;

    @JsonCreator
    private DiscountedLineItemPriceForQuantity(final DiscountedLineItemPrice discountedPrice, final Long quantity) {
        this.discountedPrice = discountedPrice;
        this.quantity = quantity;
    }

    public static DiscountedLineItemPriceForQuantity of(final DiscountedLineItemPrice discountedLineItemPrice, final long quantity) {
        return new DiscountedLineItemPriceForQuantity(discountedLineItemPrice, quantity);
    }

    public DiscountedLineItemPrice getDiscountedPrice() {
        return discountedPrice;
    }

    public Long getQuantity() {
        return quantity;
    }
}
