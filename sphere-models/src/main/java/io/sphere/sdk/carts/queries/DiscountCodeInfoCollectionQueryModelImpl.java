package io.sphere.sdk.carts.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.ReferenceQueryModel;

final class DiscountCodeInfoCollectionQueryModelImpl<T> extends QueryModelImpl<T> implements DiscountCodeInfoCollectionQueryModel<T> {
    public DiscountCodeInfoCollectionQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ReferenceQueryModel<T, DiscountCode> discountCode() {
        return referenceModel("discountCode");
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
