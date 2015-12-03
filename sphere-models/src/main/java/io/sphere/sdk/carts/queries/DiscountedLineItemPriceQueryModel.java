package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.MoneyQueryModel;

public interface DiscountedLineItemPriceQueryModel<T> {
    MoneyQueryModel<T> value();

    DiscountedLineItemPortionQueryModel<T> includedDiscounts();
}
