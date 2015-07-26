package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.CollectionQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.QueryPredicate;

import javax.annotation.Nullable;

public final class PriceCollectionQueryModel<T> extends QueryModelImpl<T> implements CollectionQueryModel<T> {

    PriceCollectionQueryModel(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
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

