package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

public class DateAttributeDefinitionBuilder extends BaseBuilder<DateAttributeDefinition> {
    public DateAttributeDefinitionBuilder(final String name, final LocalizedString label) {
        super(name, label);
    }

    @Override
    protected BaseBuilder<DateAttributeDefinition> getThis() {
        return this;
    }

    @Override
    public DateAttributeDefinition build() {
        return new DateAttributeDefinition(new DateType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable());
    }
}
