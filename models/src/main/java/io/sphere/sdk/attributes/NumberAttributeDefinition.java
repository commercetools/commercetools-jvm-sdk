package io.sphere.sdk.attributes;

import io.sphere.sdk.models.LocalizedStrings;

public class NumberAttributeDefinition extends AttributeDefinitionBase<NumberType> {
    public NumberAttributeDefinition(final NumberType attributeType, final String name, final LocalizedStrings label, final boolean isRequired, final AttributeConstraint attributeConstraint, final boolean isSearchable) {
        super(attributeType, name, label, isRequired, attributeConstraint, isSearchable);
    }
}
