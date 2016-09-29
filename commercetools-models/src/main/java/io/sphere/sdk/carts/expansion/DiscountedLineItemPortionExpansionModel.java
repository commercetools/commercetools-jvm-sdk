package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;

public interface DiscountedLineItemPortionExpansionModel<T> {
    CartDiscountExpansionModel<T> discount();
}
