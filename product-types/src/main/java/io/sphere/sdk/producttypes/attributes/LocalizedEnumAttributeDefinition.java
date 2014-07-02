package io.sphere.sdk.producttypes.attributes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;

@JsonDeserialize()
public class LocalizedEnumAttributeDefinition extends AttributeDefinitionBase {
    LocalizedEnumAttributeDefinition(final AttributeType attributeType, final String name, final LocalizedString label, final boolean isRequired, final AttributeConstraint attributeConstraint, final boolean isSearchable) {
        super(attributeType, name, label, isRequired, attributeConstraint, isSearchable);
    }
}