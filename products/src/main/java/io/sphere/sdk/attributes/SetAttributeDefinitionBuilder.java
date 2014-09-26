package io.sphere.sdk.attributes;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;

public class SetAttributeDefinitionBuilder extends AttributeDefinitionBuilder<SetAttributeDefinitionBuilder> implements Builder<SetAttributeDefinition> {
    private final AttributeType elementType;

    SetAttributeDefinitionBuilder(final String name, final LocalizedString label, final AttributeType elementType) {
        super(name, label);
        this.elementType = elementType;
    }

    @Override
    protected SetAttributeDefinitionBuilder getThis() {
        return this;
    }

    @Override
    public SetAttributeDefinition build() {
        return new SetAttributeDefinition(new SetType(elementType), getName(), getLabel(), getAttributeConstraint());
    }

    public static SetAttributeDefinitionBuilder of(final String name, final LocalizedString label, final AttributeType attributeType) {
        return new SetAttributeDefinitionBuilder(name, label, attributeType);
    }
}
