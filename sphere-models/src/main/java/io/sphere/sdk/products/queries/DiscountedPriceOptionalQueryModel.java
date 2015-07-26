package io.sphere.sdk.products.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.queries.*;

import javax.annotation.Nullable;

public final class DiscountedPriceOptionalQueryModel<T> extends QueryModelImpl<T> implements OptionalQueryModel<T> {

    DiscountedPriceOptionalQueryModel(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
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

    public ReferenceQueryModel<T, ProductDiscount> discount() {
        return referenceModel("discount");
    }
}

