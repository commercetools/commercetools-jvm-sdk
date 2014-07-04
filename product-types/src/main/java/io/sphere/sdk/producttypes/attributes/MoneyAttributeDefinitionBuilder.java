package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

public class MoneyAttributeDefinitionBuilder extends BaseBuilder<MoneyAttributeDefinition, MoneyAttributeDefinitionBuilder> {
    MoneyAttributeDefinitionBuilder(final String name, final LocalizedString label) {
        super(name, label);
    }

    @Override
    protected MoneyAttributeDefinitionBuilder getThis() {
        return this;
    }

    @Override
    public MoneyAttributeDefinition build() {
        return new MoneyAttributeDefinition(new MoneyType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable());
    }

    public static MoneyAttributeDefinitionBuilder of(final String name, final LocalizedString label) {
        return new MoneyAttributeDefinitionBuilder(name, label);
    }
}
