package io.sphere.sdk.cartdiscounts;

/**
 * The predicate offers a flexible way to define whether the discount can be applied to a cart.
 *
 * @deprecated Use {@link CartPredicate} instead.
 */
@Deprecated
public interface CartDiscountPredicate extends CartPredicate {
    String toSphereCartPredicate();

    static CartDiscountPredicate of(final String predicate) {
        return new CartDiscountPredicateImpl(predicate);
    }
}
