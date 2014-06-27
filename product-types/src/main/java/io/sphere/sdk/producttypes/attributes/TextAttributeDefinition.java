package io.sphere.sdk.producttypes.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.LocalizedString;

/**
 * @see TextAttributeDefinitionBuilder
 */
public final class TextAttributeDefinition extends AttributeDefinitionBase {
    private final TextInputHint textInputHint;

    TextAttributeDefinition(final AttributeType type, final String name, final LocalizedString label,
                                    final boolean isRequired, final AttributeConstraint attributeConstraint,
                                    final boolean isSearchable, final TextInputHint textInputHint) {
        super(type, name, label, isRequired, attributeConstraint, isSearchable);
        this.textInputHint = textInputHint;
    }

    @JsonProperty("inputHint")
    public TextInputHint getTextInputHint() {
        return textInputHint;
    }
}
