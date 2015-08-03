package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.MoneyQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.QueryPredicate;

final class TaxedPriceOptionalQueryModelImpl<T> extends QueryModelImpl<T> implements TaxedPriceOptionalQueryModel<T> {

    public TaxedPriceOptionalQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return isNotPresentPredicate();
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return isPresentPredicate();
    }

    @Override
    public MoneyQueryModel<T> totalNet() {
        return moneyModel("totalNet");
    }

    @Override
    public MoneyQueryModel<T> totalGross() {
        return moneyModel("totalGross");
    }
}
