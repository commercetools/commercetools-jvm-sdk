package io.sphere.sdk.producttypes.attributes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;

@JsonDeserialize()
public final class LocalizedTextAttributeDefinition extends TextLikeAttributeDefinition<LocalizedTextType> {

    public LocalizedTextAttributeDefinition(final LocalizedTextType attributeType, final String name, final LocalizedString label,
                                            final boolean isRequired, final AttributeConstraint attributeConstraint,
                                            final boolean isSearchable, final TextInputHint textInputHint) {
        super(attributeType, name, label, isRequired, attributeConstraint, isSearchable, textInputHint);
    }
}
