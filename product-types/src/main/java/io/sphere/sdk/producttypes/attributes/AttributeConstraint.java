package io.sphere.sdk.producttypes.attributes;

public enum  AttributeConstraint {
    /**
     * No constraints are applied to the attribute.
     */
    None,
    /**
     * Attribute value should be different in each variant.
     */
    Unique,
    /**
     * A set of attributes, that have this constraint, should have different combinations in each variant.
     */
    CombinationUnique,
    /**
     * Attribute value should be the same in all variants
     */
    SameForAll
}
