package io.sphere.sdk.attributes;

import io.sphere.sdk.models.LocalizedString;

public class NumberAttributeDefinition extends AttributeDefinitionBase<NumberType> {
    public NumberAttributeDefinition(final NumberType attributeType, final String name, final LocalizedString label, final boolean isRequired, final AttributeConstraint attributeConstraint, final boolean isSearchable) {
        super(attributeType, name, label, isRequired, attributeConstraint, isSearchable);
    }
}
