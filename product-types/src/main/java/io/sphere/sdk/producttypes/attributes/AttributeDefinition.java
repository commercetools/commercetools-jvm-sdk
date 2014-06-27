package io.sphere.sdk.producttypes.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.LocalizedString;

/**
 * Describes the structure and validation logic of a product attribute.
 */
public class AttributeDefinition {

    private final AttributeType type;
    private final String name;
    private final LocalizedString label;
    private final boolean isRequired;
    private final AttributeConstraint attributeConstraint;
    private final boolean isSearchable;

    @JsonCreator
    AttributeDefinition(AttributeType type, @JsonProperty("name") String name, LocalizedString label, boolean isRequired,
                                  AttributeConstraint attributeConstraint, boolean isSearchable) {
        this.type = type;
        this.name = name;
        this.label = label;
        this.isRequired = isRequired;
        this.attributeConstraint = attributeConstraint;
        this.isSearchable = isSearchable;
    }

    /**
     * Describes the type of the attribute.
     * @return the type of the attribute
     */
    public AttributeType getType() {
        return type;
    }

    /**
     * The unique name of the attribute used in the API.
     * @return name of the attribute
     */
    public String getName() {
        return name;
    }

    /**
     * A human-readable label for the attribute.
     * @return label for the attribute
     */
    public LocalizedString getLabel() {
        return label;
    }

    /**
     * Whether the attribute is required to have a value.
     * @return true if required else false
     */
    public boolean isRequired() {
        return isRequired;
    }

    /**
     * Describes how an attribute or a set of attributes should be validated across all variants of a product.
     * @return definition of validation logic
     */
    public AttributeConstraint getAttributeConstraint() {
        return attributeConstraint;
    }

    /**
     * Whether the attribute's values should generally be enabled in product search.
     * The exact features that are enabled/disabled with this flag depend on the concrete attribute type and are described there.
     *
     * @return true if searchable, false if not
     */
    public boolean isSearchable() {
        return isSearchable;
    }
}
