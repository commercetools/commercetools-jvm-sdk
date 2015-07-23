package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.CollectionQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.QueryPredicate;

public final class PriceCollectionQueryModel<T> extends QueryModelImpl<T> implements CollectionQueryModel<T> {

    PriceCollectionQueryModel(QueryModel<T> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    public DiscountedPriceOptionalQueryModel<T> discounted() {
        return new DiscountedPriceOptionalQueryModel<>(this, "discounted");
    }

    @Override
    public QueryPredicate<T> isEmpty() {
        return isEmptyCollectionQueryPredicate();
    }

    @Override
    public QueryPredicate<T> isNotEmpty() {
        return isNotEmptyCollectionQueryPredicate();
    }
}

