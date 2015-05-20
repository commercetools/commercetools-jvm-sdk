package io.sphere.sdk.cartdiscounts;

public interface CartDiscountPredicate {
    String toSphereCartDiscountPredicate();

    static CartDiscountPredicate of(final String predicate) {
        return new CartDiscountPredicateImpl(predicate);
    }
}
