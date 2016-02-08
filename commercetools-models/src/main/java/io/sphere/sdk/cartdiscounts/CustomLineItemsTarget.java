package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public final class CustomLineItemsTarget extends Base implements CartDiscountTarget {
    private final String predicate;

    @JsonCreator
    private CustomLineItemsTarget(final String predicate) {
        this.predicate = predicate;
    }

    public String getPredicate() {
        return predicate;
    }

    public static CustomLineItemsTarget of(final String predicate) {
        return new CustomLineItemsTarget(predicate);
    }
}
