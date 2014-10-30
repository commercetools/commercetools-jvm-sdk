package io.sphere.sdk.attributes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedStrings;

@JsonDeserialize()
public class EnumAttributeDefinition extends AttributeDefinitionBase<EnumType> {
    EnumAttributeDefinition(final EnumType attributeType, final String name, final LocalizedStrings label, final boolean isRequired, final AttributeConstraint attributeConstraint, final boolean isSearchable) {
        super(attributeType, name, label, isRequired, attributeConstraint, isSearchable);
    }
}
