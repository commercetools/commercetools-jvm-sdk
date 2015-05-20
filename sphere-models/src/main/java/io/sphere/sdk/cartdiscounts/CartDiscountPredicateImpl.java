package io.sphere.sdk.cartdiscounts;


import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

final class CartDiscountPredicateImpl extends Base implements CartDiscountPredicate {
    private final String predicate;

    @JsonCreator
    public CartDiscountPredicateImpl(final String predicate) {
        this.predicate = predicate;
    }

    @Override
    public String toSphereCartDiscountPredicate() {
        return predicate;
    }

    @Override
    public final boolean equals(Object o) {
        return o != null && o instanceof CartDiscountPredicate && toSphereCartDiscountPredicate().equals(((CartDiscountPredicate) o).toSphereCartDiscountPredicate());
    }

    @Override
    public final int hashCode() {
        return toSphereCartDiscountPredicate().hashCode();
    }
}
