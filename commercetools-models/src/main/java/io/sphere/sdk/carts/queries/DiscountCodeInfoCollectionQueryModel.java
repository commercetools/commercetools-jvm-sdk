package io.sphere.sdk.carts.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.queries.CollectionQueryModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.ReferenceQueryModel;

public interface DiscountCodeInfoCollectionQueryModel<T> extends CollectionQueryModel<T> {

    @Override
    QueryPredicate<T> isEmpty();

    @Override
    QueryPredicate<T> isNotEmpty();

    ReferenceQueryModel<T, DiscountCode> discountCode();
}
