package io.sphere.sdk.products.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.queries.OptionalQueryModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.ReferenceQueryModel;

public interface DiscountedPriceOptionalQueryModel<T> extends OptionalQueryModel<T> {
    @Override
    QueryPredicate<T> isPresent();

    @Override
    QueryPredicate<T> isNotPresent();

    ReferenceQueryModel<T, ProductDiscount> discount();
}
