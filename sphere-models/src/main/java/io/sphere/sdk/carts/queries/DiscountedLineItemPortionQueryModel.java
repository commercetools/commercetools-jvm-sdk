package io.sphere.sdk.carts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.queries.MoneyQueryModel;
import io.sphere.sdk.queries.ReferenceQueryModel;

public interface DiscountedLineItemPortionQueryModel<T> {
    ReferenceQueryModel<T, CartDiscount> discount();

    MoneyQueryModel<T> discountedAmount();
}
