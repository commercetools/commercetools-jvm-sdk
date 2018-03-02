package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.TextInputHint;

import javax.annotation.Nullable;

/**
 * Template to create a new Attribute Definition.
 */
@JsonDeserialize(as = AttributeDefinitionDraftDsl.class)
@ResourceDraftValue(
        abstractBuilderClass = true,
        factoryMethods = @FactoryMethod(parameterNames = {"attributeType", "name", "label", "required"})
)
public interface AttributeDefinitionDraft {

    @JsonProperty("type")
    AttributeType getAttributeType();

    String getName();

    LocalizedString getLabel();

    @JsonProperty("isRequired")
    Boolean isRequired();

    @Nullable
    AttributeConstraint getAttributeConstraint();

    @JsonProperty("isSearchable")
    Boolean isSearchable();

    @JsonProperty("inputHint")
    TextInputHint getInputHint();

    @Nullable
    LocalizedString getInputTip();
}
