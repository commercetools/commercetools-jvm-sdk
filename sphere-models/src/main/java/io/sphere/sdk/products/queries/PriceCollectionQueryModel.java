package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.*;

import java.util.Optional;

public final class PriceCollectionQueryModel<T> extends QueryModelImpl<T> implements CollectionQueryModel<T> {

    PriceCollectionQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public DiscountedPriceOptionalQueryModel<T> discounted() {
        return new DiscountedPriceOptionalQueryModel<>(Optional.of(this), Optional.of("discounted"));
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

