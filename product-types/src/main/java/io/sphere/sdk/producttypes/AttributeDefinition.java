package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.LocalizedString;

/**
 * Describes the structure and validation logic of a product attribute.
 */
public interface AttributeDefinition {
    /**
     * Describes the type of the attribute.
     * @return the type of the attribute
     */
    AttributeType getType();

    /**
     * The unique name of the attribute used in the API.
     * @return name of the attribute
     */
    String getName();

    /**
     * A human-readable label for the attribute.
     * @return label for the attribute
     */
    LocalizedString getLabel();

    /**
     * Whether the attribute is required to have a value.
     * @return true if required else false
     */
    boolean isRequired();

    /**
     * Describes how an attribute or a set of attributes should be validated across all variants of a product.
     * @return definition of validation logic
     */
    AttributeConstraint getAttributeConstraint();

    /**
     * Whether the attribute's values should generally be enabled in product search.
     * The exact features that are enabled/disabled with this flag depend on the concrete attribute type and are described there.
     *
     * @return true if searchable, false if not
     */
    boolean isSearchable();
}
