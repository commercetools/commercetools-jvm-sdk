package io.sphere.sdk.productdiscounts;

public interface ProductDiscountPredicate {
    String toSpherePredicate();

    static ProductDiscountPredicate of(final String predicate) {
        return new ProductDiscountPredicateImpl(predicate);
    }
}
