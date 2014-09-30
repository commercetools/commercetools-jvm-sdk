package io.sphere.sdk.attributes;

import io.sphere.sdk.models.LocalizedString;

public class DateAttributeDefinitionBuilder extends BaseBuilder<DateAttributeDefinition, DateAttributeDefinitionBuilder> {
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
