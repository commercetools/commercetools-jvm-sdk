package io.sphere.sdk.carts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.queries.MoneyQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.ReferenceQueryModel;

final class DiscountedLineItemPortionQueryModelImpl<T> extends QueryModelImpl<T> implements DiscountedLineItemPortionQueryModel<T> {
    public DiscountedLineItemPortionQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ReferenceQueryModel<T, CartDiscount> discount() {
        return referenceModel("discount");
    }

    @Override
    public MoneyQueryModel<T> discountedAmount() {
        return moneyModel("discountedAmount");
    }
}
