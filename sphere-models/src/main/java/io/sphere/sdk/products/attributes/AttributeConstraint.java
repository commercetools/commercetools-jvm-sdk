package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * AttributeConstraint enum tells how an attribute or a set of attributes should be validated across all variants of a product.
 */
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
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
