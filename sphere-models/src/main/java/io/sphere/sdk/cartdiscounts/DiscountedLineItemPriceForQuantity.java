package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public class DiscountedLineItemPriceForQuantity extends Base {
    private final Integer quantity;
    private final DiscountedLineItemPrice discountedPrice;

    @JsonCreator
    private DiscountedLineItemPriceForQuantity(final DiscountedLineItemPrice discountedPrice, final Integer quantity) {
        this.discountedPrice = discountedPrice;
        this.quantity = quantity;
    }

    public static DiscountedLineItemPriceForQuantity of(final DiscountedLineItemPrice discountedLineItemPrice, final Integer quantity) {
        return new DiscountedLineItemPriceForQuantity(discountedLineItemPrice, quantity);
    }

    public DiscountedLineItemPrice getDiscountedPrice() {
        return discountedPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
