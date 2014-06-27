package io.sphere.sdk.producttypes.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.LocalizedString;

/**
 * @see TextAttributeDefinitionBuilder
 */
public final class TextAttributeDefinition extends TextLikeAttributeDefinition {

    public TextAttributeDefinition(final AttributeType attributeType, final String name, final LocalizedString label,
                                   final boolean isRequired, final AttributeConstraint attributeConstraint,
                                   final boolean isSearchable, final TextInputHint textInputHint) {
        super(attributeType, name, label, isRequired, attributeConstraint, isSearchable, textInputHint);
    }
}
