package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.TextInputHint;

import javax.annotation.Nullable;

/**
 * Attribute definitions describe a product attribute and allow you to define some meta-information associated with the attribute (like whether it should be searchable or its constraints).
 *
 * @see AttributeDefinitionBuilder
 */
@JsonDeserialize(as = AttributeDefinitionImpl.class)
public interface AttributeDefinition {
    /**
     * Describes the type of the attribute.
     * @return the type of the attribute
     */
    @JsonProperty("type")
    AttributeType getAttributeType();

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
    @JsonProperty("isRequired")
    Boolean isRequired();

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
    @JsonProperty("isSearchable")
    Boolean isSearchable();

    @JsonProperty("inputHint")
    TextInputHint getInputHint();

    /**
     * Additional information about the attribute that aids content managers when setting product details.
     * @return input tip
     */
    @Nullable
    LocalizedString getInputTip();
}
