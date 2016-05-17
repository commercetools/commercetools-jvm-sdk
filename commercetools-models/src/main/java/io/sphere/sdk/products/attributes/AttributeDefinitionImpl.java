package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.TextInputHint;

import javax.annotation.Nullable;

final class AttributeDefinitionImpl extends Base implements AttributeDefinition {
    private final AttributeType attributeType;
    private final String name;
    private final LocalizedString label;
    private final Boolean isRequired;
    private final AttributeConstraint attributeConstraint;
    private final Boolean isSearchable;
    private final TextInputHint inputHint;
    @Nullable
    private final LocalizedString inputTip;

    @JsonCreator
    AttributeDefinitionImpl(AttributeType attributeType, String name, LocalizedString label, Boolean isRequired,
                            AttributeConstraint attributeConstraint, Boolean isSearchable, TextInputHint inputHint, @Nullable final LocalizedString inputTip) {
        this.attributeType = attributeType;
        this.name = name;
        this.label = label;
        this.isRequired = isRequired;
        this.attributeConstraint = attributeConstraint;
        this.isSearchable = isSearchable;
        this.inputHint = inputHint;
        this.inputTip = inputTip;
    }

    /**
     * Describes the type of the attribute.
     * @return the type of the attribute
     */
    @Override
    @JsonProperty("type")
    public AttributeType getAttributeType() {
        return attributeType;
    }

    /**
     * The unique name of the attribute used in the API.
     * @return name of the attribute
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * A human-readable label for the attribute.
     * @return label for the attribute
     */
    @Override
    public LocalizedString getLabel() {
        return label;
    }

    /**
     * Whether the attribute is required to have a value.
     * @return true if required else false
     */
    @Override
    @JsonProperty("isRequired")
    public Boolean isRequired() {
        return isRequired;
    }

    /**
     * Describes how an attribute or a set of attributes should be validated across all variants of a product.
     * @return definition of validation logic
     */
    @Override
    public AttributeConstraint getAttributeConstraint() {
        return attributeConstraint;
    }

    /**
     * Whether the attribute's values should generally be enabled in product search.
     * The exact features that are enabled/disabled with this flag depend on the concrete attribute type and are described there.
     *
     * @return true if searchable, false if not
     */
    @Override
    @JsonProperty("isSearchable")
    public Boolean isSearchable() {
        return isSearchable;
    }

    @Override
    @JsonProperty("inputHint")
    public TextInputHint getInputHint() {
        return inputHint;
    }

    @Override
    @Nullable
    public LocalizedString getInputTip() {
        return inputTip;
    }
}
