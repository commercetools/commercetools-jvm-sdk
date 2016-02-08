package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ProductDiscountPredicateImpl.class)
public interface ProductDiscountPredicate {
    String toSpherePredicate();

    static ProductDiscountPredicate of(final String predicate) {
        return new ProductDiscountPredicateImpl(predicate);
    }
}
