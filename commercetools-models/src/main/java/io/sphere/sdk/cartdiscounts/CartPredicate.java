package io.sphere.sdk.cartdiscounts;

/**
 * The predicate offers a flexible way to define whether the discount or shipping method can be applied to a cart.
 */
public interface CartPredicate {
    String toSphereCartPredicate();

    static CartPredicate of(final String predicate) {
        return new CartPredicateImpl(predicate);
    }
}
