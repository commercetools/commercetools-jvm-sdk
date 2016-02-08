package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.MoneyQueryModel;
import io.sphere.sdk.queries.OptionalQueryModel;
import io.sphere.sdk.queries.QueryPredicate;

public interface DiscountedLineItemPriceQueryModel<T> extends OptionalQueryModel<T> {
    MoneyQueryModel<T> value();

    DiscountedLineItemPortionQueryModel<T> includedDiscounts();

    @Override
    QueryPredicate<T> isNotPresent();

    @Override
    QueryPredicate<T> isPresent();
}
