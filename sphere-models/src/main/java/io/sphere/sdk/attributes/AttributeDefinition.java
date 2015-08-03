package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;

/**
 * @see AttributeDefinitionBuilder
 */
public class AttributeDefinition extends Base {
    private final AttributeType attributeType;
    private final String name;
    private final LocalizedStrings label;
    private final Boolean isRequired;
    private final AttributeConstraint attributeConstraint;
    private final Boolean isSearchable;
    private final TextInputHint inputHint;

    @JsonCreator
    AttributeDefinition(AttributeType attributeType, String name, LocalizedStrings label, Boolean isRequired,
                        AttributeConstraint attributeConstraint, Boolean isSearchable, TextInputHint inputHint) {
        this.attributeType = attributeType;
        this.name = name;
        this.label = label;
        this.isRequired = isRequired;
        this.attributeConstraint = attributeConstraint;
        this.isSearchable = isSearchable;
        this.inputHint = inputHint;
    }

    /**
     * Describes the type of the attribute.
     * @return the type of the attribute
     */
    @JsonProperty("type")
    public AttributeType getAttributeType() {
        return attributeType;
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
    public LocalizedStrings getLabel() {
        return label;
    }

    /**
     * Whether the attribute is required to have a value.
     * @return true if required else false
     */
    public Boolean getIsRequired() {
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
    public Boolean getIsSearchable() {
        return isSearchable;
    }

    @JsonProperty("inputHint")
    public TextInputHint getInputHint() {
        return inputHint;
    }
}
