package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum AttributeConstraint implements SphereEnumeration {
    /**
     * No constraints are applied to the attribute.
     */
    NONE,
    /**
     * Attribute value should be different in each variant.
     */
    UNIQUE,
    /**
     * A set of attributes, that have this constraint, should have different combinations in each variant.
     */
    COMBINATION_UNIQUE,
    /**
     * Attribute value should be the same in all variants
     */
    SAME_FOR_ALL;

    @JsonCreator
    public static AttributeConstraint ofSphereValue(final String value) {
        return SphereEnumeration.find(values(), value);
    }
}
