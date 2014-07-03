package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

public class NumberAttributeDefinitionBuilder extends BaseBuilder<NumberAttributeDefinition> {
    NumberAttributeDefinitionBuilder(final String name, final LocalizedString label) {
        super(name, label);
    }

    @Override
    protected BaseBuilder<NumberAttributeDefinition> getThis() {
        return this;
    }

    @Override
    public NumberAttributeDefinition build() {
        return new NumberAttributeDefinition(new NumberType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable());
    }
}
