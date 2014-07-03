package io.sphere.sdk.producttypes.attributes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;

@JsonDeserialize()
public final class TextAttributeDefinition extends TextLikeAttributeDefinition<TextType> {

    public TextAttributeDefinition(final TextType attributeType, final String name, final LocalizedString label,
                                   final boolean isRequired, final AttributeConstraint attributeConstraint,
                                   final boolean isSearchable, final TextInputHint textInputHint) {
        super(attributeType, name, label, isRequired, attributeConstraint, isSearchable, textInputHint);
    }
}
