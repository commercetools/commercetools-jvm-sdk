package io.sphere.sdk.carts.expansion;

public interface CustomLineItemExpansionModel<T> {

    DiscountedLineItemPricePerQuantityExpansionModel<T> discountedPricePerQuantity();

    DiscountedLineItemPricePerQuantityExpansionModel<T> discountedPricePerQuantity(int index);
}
