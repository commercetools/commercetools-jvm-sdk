package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

final class ProductDiscountPredicateImpl extends Base implements ProductDiscountPredicate {
    private final String predicate;

    @JsonCreator
    public ProductDiscountPredicateImpl(final String predicate) {
        this.predicate = predicate;
    }

    @Override
    public String toSpherePredicate() {
        return predicate;
    }
}
