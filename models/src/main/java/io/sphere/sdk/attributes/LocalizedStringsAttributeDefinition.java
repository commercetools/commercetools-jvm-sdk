package io.sphere.sdk.attributes;

import io.sphere.sdk.models.LocalizedStrings;

public final class LocalizedStringsAttributeDefinition extends TextLikeAttributeDefinition<LocalizedStringsType> {

    public LocalizedStringsAttributeDefinition(final LocalizedStringsType attributeType, final String name, final LocalizedStrings label,
                                               final boolean isRequired, final AttributeConstraint attributeConstraint,
                                               final boolean isSearchable, final TextInputHint textInputHint) {
        super(attributeType, name, label, isRequired, attributeConstraint, isSearchable, textInputHint);
    }
}
