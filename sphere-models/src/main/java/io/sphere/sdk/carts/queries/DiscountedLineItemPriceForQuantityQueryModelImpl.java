package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.LongQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;

final class DiscountedLineItemPriceForQuantityQueryModelImpl<T> extends QueryModelImpl<T> implements DiscountedLineItemPriceForQuantityQueryModel<T> {
    public DiscountedLineItemPriceForQuantityQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public DiscountedLineItemPriceQueryModel<T> discountedPrice() {
        return new DiscountedLineItemPriceQueryModelImpl<>(this, "discountedPrice");
    }

    @Override
    public LongQueryModel<T> quantity() {
        return longModel("quantity");
    }
}
