package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.MoneyQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.QueryPredicate;

final class DiscountedLineItemPriceQueryModelImpl<T> extends QueryModelImpl<T> implements DiscountedLineItemPriceQueryModel<T> {
    public DiscountedLineItemPriceQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public DiscountedLineItemPortionQueryModel<T> includedDiscounts() {
        return new DiscountedLineItemPortionQueryModelImpl<>(this, "includedDiscounts");
    }

    @Override
    public MoneyQueryModel<T> value() {
        return moneyModel("value");
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return isNotPresentPredicate();
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return isPresentPredicate();
    }
}
