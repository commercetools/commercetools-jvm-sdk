package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

import java.util.List;


public class LocalizedEnumAttributeDefinitionBuilder extends BaseBuilder<LocalizedEnumAttributeDefinition> {
    private final List<LocalizedEnumValue> values;

    LocalizedEnumAttributeDefinitionBuilder(final String name, final LocalizedString label, final List<LocalizedEnumValue> values) {
        super(name, label);
        this.values = values;
    }

    @Override
    protected LocalizedEnumAttributeDefinitionBuilder getThis() {
        return this;
    }

    @Override
    public LocalizedEnumAttributeDefinition build() {
        return new LocalizedEnumAttributeDefinition(new LocalizedEnumType(values), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable());
    }

    public static LocalizedEnumAttributeDefinitionBuilder of(final String name, final LocalizedString label, final List<LocalizedEnumValue> values) {
        return new LocalizedEnumAttributeDefinitionBuilder(name, label, values);
    }
}
