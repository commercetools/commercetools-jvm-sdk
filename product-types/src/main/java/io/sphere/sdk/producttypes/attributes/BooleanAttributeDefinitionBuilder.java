package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

public class BooleanAttributeDefinitionBuilder extends BaseBuilder<BooleanAttributeDefinition> {
    public BooleanAttributeDefinitionBuilder(final String name, final LocalizedString label) {
        super(name, label);
    }

    @Override
    protected BaseBuilder<BooleanAttributeDefinition> getThis() {
        return this;
    }

    @Override
    public BooleanAttributeDefinition build() {
        return new BooleanAttributeDefinition(new BooleanType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable());
    }
}
