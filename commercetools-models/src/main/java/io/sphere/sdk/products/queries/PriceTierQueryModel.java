package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.*;

public interface PriceTierQueryModel<T> extends OptionalQueryModel<T>, CollectionQueryModel<T> {

    @Override
    QueryPredicate<T> isNotPresent();

    @Override
    QueryPredicate<T> isPresent();

    @Override
    QueryPredicate<T> isEmpty();

    @Override
    QueryPredicate<T> isNotEmpty();

    MoneyQueryModel<T> value();

    IntegerQueryModel<T> minimumQuantity();
}
