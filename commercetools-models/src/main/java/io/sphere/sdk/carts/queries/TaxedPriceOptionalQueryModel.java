package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.MoneyQueryModel;
import io.sphere.sdk.queries.OptionalQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryPredicate;

public interface TaxedPriceOptionalQueryModel<T> extends QueryModel<T>, OptionalQueryModel<T> {
    @Override
    QueryPredicate<T> isNotPresent();

    @Override
    QueryPredicate<T> isPresent();

    MoneyQueryModel<T> totalNet();

    MoneyQueryModel<T> totalGross();
}
