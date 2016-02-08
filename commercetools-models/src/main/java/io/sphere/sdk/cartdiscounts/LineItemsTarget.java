package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public final class LineItemsTarget extends Base implements CartDiscountTarget {
    private final String predicate;

    @JsonCreator
    private LineItemsTarget(final String predicate) {
        this.predicate = predicate;
    }

    public String getPredicate() {
        return predicate;
    }

    public static LineItemsTarget ofAll() {
        return of("1 = 1");
    }

    public static LineItemsTarget of(final String predicate) {
        return new LineItemsTarget(predicate);
    }
}
