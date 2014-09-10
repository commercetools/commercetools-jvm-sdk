package io.sphere.sdk.attributes;

import io.sphere.sdk.models.LocalizedString;

public class TimeAttributeDefinitionBuilder extends BaseBuilder<TimeAttributeDefinition, TimeAttributeDefinitionBuilder> {
    TimeAttributeDefinitionBuilder(final String name, final LocalizedString label) {
        super(name, label);
    }

    @Override
    protected TimeAttributeDefinitionBuilder getThis() {
        return this;
    }

    @Override
    public TimeAttributeDefinition build() {
        return new TimeAttributeDefinition(new TimeType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable());
    }

    public static TimeAttributeDefinitionBuilder of(final String name, final LocalizedString label) {
        return new TimeAttributeDefinitionBuilder(name, label);
    }
}
