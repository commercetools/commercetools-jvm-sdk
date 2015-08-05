package io.sphere.sdk.cartdiscounts;

public interface CartDiscountPredicate {
    String toSphereCartPredicate();

    static CartDiscountPredicate of(final String predicate) {
        return new CartDiscountPredicateImpl(predicate);
    }
}
