package io.sphere.sdk.cartdiscounts;


import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

final class CartPredicateImpl extends Base implements CartPredicate {
    private final String predicate;

    @JsonCreator
    public CartPredicateImpl(final String predicate) {
        this.predicate = predicate;
    }

    @Override
    public String toSphereCartPredicate() {
        return predicate;
    }

    @Override
    public final boolean equals(Object o) {
        return o != null && o instanceof CartPredicate && toSphereCartPredicate().equals(((CartPredicate) o).toSphereCartPredicate());
    }

    @Override
    public final int hashCode() {
        return toSphereCartPredicate().hashCode();
    }
}
