package io.sphere.sdk.attributes;

import io.sphere.sdk.models.LocalizedStrings;

public class LocalizedStringsAttributeDefinitionBuilder extends BaseBuilder<LocalizedStringsAttributeDefinition, LocalizedStringsAttributeDefinitionBuilder> {

    private final TextInputHint textInputHint;

    LocalizedStringsAttributeDefinitionBuilder(final String name, final LocalizedStrings label, final TextInputHint textInputHint) {
        super(name, label);
        this.textInputHint = textInputHint;
    }

    @Override
    protected LocalizedStringsAttributeDefinitionBuilder getThis() {
        return this;
    }

    @Override
    public LocalizedStringsAttributeDefinition build() {
        return new LocalizedStringsAttributeDefinition(new LocalizedStringsType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable(), textInputHint);
    }

    public static LocalizedStringsAttributeDefinitionBuilder of(final String name, final LocalizedStrings label, final TextInputHint textInputHint) {
        return new LocalizedStringsAttributeDefinitionBuilder(name, label, textInputHint);
    }
}
