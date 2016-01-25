package io.sphere.sdk.products.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.queries.*;

import javax.annotation.Nullable;

final class DiscountedPriceOptionalQueryModelImpl<T> extends QueryModelImpl<T> implements DiscountedPriceOptionalQueryModel<T> {

    DiscountedPriceOptionalQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return isPresentPredicate();
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return isNotPresentPredicate();
    }

    @Override
    public ReferenceQueryModel<T, ProductDiscount> discount() {
        return referenceModel("discount");
    }
}

