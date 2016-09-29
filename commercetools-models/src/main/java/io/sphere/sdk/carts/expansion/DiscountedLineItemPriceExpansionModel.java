package io.sphere.sdk.carts.expansion;

public interface DiscountedLineItemPriceExpansionModel<T> {
    DiscountedLineItemPortionExpansionModel<T> includedDiscounts();
}
