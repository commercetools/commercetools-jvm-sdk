package io.sphere.sdk.producttypes.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.LocalizedString;

abstract class TextLikeAttributeDefinition<T extends AttributeType> extends AttributeDefinitionBase<T> {
    private final TextInputHint textInputHint;

    TextLikeAttributeDefinition(final T attributeType, final String name, final LocalizedString label,
                                final boolean isRequired, final AttributeConstraint attributeConstraint,
                                final boolean isSearchable, final TextInputHint textInputHint) {
        super(attributeType, name, label, isRequired, attributeConstraint, isSearchable);
        this.textInputHint = textInputHint;
    }


    @JsonProperty("inputHint")
    public TextInputHint getTextInputHint() {
        return textInputHint;
    }
}
