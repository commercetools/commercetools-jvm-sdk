package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

public class DateTimeAttributeDefinitionBuilder extends BaseBuilder<DateTimeAttributeDefinition, DateTimeAttributeDefinitionBuilder> {
    DateTimeAttributeDefinitionBuilder(final String name, final LocalizedString label) {
        super(name, label);
    }

    @Override
    protected DateTimeAttributeDefinitionBuilder getThis() {
        return this;
    }

    @Override
    public DateTimeAttributeDefinition build() {
        return new DateTimeAttributeDefinition(new DateTimeType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable());
    }

    public static DateTimeAttributeDefinitionBuilder of(final String name, final LocalizedString label) {
        return new DateTimeAttributeDefinitionBuilder(name, label);
    }
}
