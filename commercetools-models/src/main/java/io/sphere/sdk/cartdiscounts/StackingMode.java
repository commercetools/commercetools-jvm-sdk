package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * Describes how this discount interacts with other discounts.
 */
public enum StackingMode implements SphereEnumeration {
    /**
     * Default. Continue applying other matching discounts after applying this one.
     */
    STACKING,
    /**
     * Don't apply any more matching discounts after this one if it got successfully applied.
     */
    STOP_AFTER_THIS_DISCOUNT;

    @JsonCreator
    public static StackingMode ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
