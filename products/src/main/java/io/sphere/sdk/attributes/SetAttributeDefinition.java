package io.sphere.sdk.attributes;

import io.sphere.sdk.models.LocalizedStrings;

public class SetAttributeDefinition extends AttributeDefinitionBase<SetType> {
    SetAttributeDefinition(final SetType attributeType, final String name, final LocalizedStrings label, final AttributeConstraint attributeConstraint) {
        super(attributeType, name, label, false, attributeConstraint, false);
    }
}
