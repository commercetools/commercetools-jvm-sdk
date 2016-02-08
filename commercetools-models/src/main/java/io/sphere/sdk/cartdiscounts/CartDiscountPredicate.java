package io.sphere.sdk.cartdiscounts;

/**
 * The predicate offers a flexible way to define whether the discount can be applied to a cart.
 */
public interface CartDiscountPredicate {
    String toSphereCartPredicate();

    static CartDiscountPredicate of(final String predicate) {
        return new CartDiscountPredicateImpl(predicate);
    }
}
