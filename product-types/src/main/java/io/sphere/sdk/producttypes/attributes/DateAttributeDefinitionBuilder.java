package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

public class DateAttributeDefinitionBuilder extends BaseBuilder<DateAttributeDefinition> {
    DateAttributeDefinitionBuilder(final String name, final LocalizedString label) {
        super(name, label);
    }

    @Override
    protected DateAttributeDefinitionBuilder getThis() {
        return this;
    }

    @Override
    public DateAttributeDefinition build() {
        return new DateAttributeDefinition(new DateType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable());
    }

    public static DateAttributeDefinitionBuilder of(final String name, final LocalizedString label) {
        return new DateAttributeDefinitionBuilder(name, label);
    }
}
