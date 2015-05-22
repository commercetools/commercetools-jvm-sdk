package io.sphere.sdk.cartdiscounts;

public interface CartPredicate {
    String toSphereCartPredicate();

    static CartPredicate of(final String predicate) {
        return new CartPredicateImpl(predicate);
    }
}
