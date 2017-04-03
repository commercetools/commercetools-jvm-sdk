package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.*;

import javax.annotation.Nullable;

final class PriceTierQueryModelImpl<T> extends QueryModelImpl<T> implements PriceTierQueryModel<T> {
    PriceTierQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
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
    public QueryPredicate<T> isEmpty() {
        return isEmptyCollectionQueryPredicate();
    }

    @Override
    public QueryPredicate<T> isNotEmpty() {
        return isNotEmptyCollectionQueryPredicate();
    }

    @Override
    public MoneyQueryModel<T> value() {
        return moneyQueryModel("value");
    }

    @Override
    public IntegerQueryModel<T> minimumQuantity() {
        return integerModel("minimumQuantity");
    }
}
