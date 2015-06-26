package io.sphere.sdk.productdiscounts;

import io.sphere.sdk.models.Base;

final class ProductDiscountPredicateImpl extends Base implements ProductDiscountPredicate {
    private final String predicate;

    public ProductDiscountPredicateImpl(final String predicate) {
        this.predicate = predicate;
    }

    @Override
    public String toSpherePredicate() {
        return predicate;
    }
}
