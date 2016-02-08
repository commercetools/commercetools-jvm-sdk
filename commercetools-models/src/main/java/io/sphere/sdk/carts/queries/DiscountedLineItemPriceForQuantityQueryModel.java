package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.LongQueryModel;

public interface DiscountedLineItemPriceForQuantityQueryModel<T> {
    LongQueryModel<T> quantity();

    DiscountedLineItemPriceQueryModel<T> discountedPrice();
}
