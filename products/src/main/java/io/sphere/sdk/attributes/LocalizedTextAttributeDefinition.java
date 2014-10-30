package io.sphere.sdk.attributes;

import io.sphere.sdk.models.LocalizedStrings;

public final class LocalizedTextAttributeDefinition extends TextLikeAttributeDefinition<LocalizedTextType> {

    public LocalizedTextAttributeDefinition(final LocalizedTextType attributeType, final String name, final LocalizedStrings label,
                                            final boolean isRequired, final AttributeConstraint attributeConstraint,
                                            final boolean isSearchable, final TextInputHint textInputHint) {
        super(attributeType, name, label, isRequired, attributeConstraint, isSearchable, textInputHint);
    }
}
