package io.sphere.sdk.attributes;

import io.sphere.sdk.models.LocalizedStrings;

public class NumberAttributeDefinitionBuilder extends BaseBuilder<NumberAttributeDefinition, NumberAttributeDefinitionBuilder> {
    NumberAttributeDefinitionBuilder(final String name, final LocalizedStrings label) {
        super(name, label);
    }

    @Override
    protected NumberAttributeDefinitionBuilder getThis() {
        return this;
    }

    @Override
    public NumberAttributeDefinition build() {
        return new NumberAttributeDefinition(new NumberType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable());
    }

    public static NumberAttributeDefinitionBuilder of(final String name, final LocalizedStrings label) {
        return new NumberAttributeDefinitionBuilder(name, label);
    }
}
