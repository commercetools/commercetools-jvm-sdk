package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

public final class LocalizedTextAttributeDefinition extends TextLikeAttributeDefinition {

    public LocalizedTextAttributeDefinition(final AttributeType attributeType, final String name, final LocalizedString label,
                                            final boolean isRequired, final AttributeConstraint attributeConstraint,
                                            final boolean isSearchable, final TextInputHint textInputHint) {
        super(attributeType, name, label, isRequired, attributeConstraint, isSearchable, textInputHint);
    }
}
