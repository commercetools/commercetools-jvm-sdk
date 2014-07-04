package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

public class LocalizedTextAttributeDefinitionBuilder extends BaseBuilder<LocalizedTextAttributeDefinition> {

    private final TextInputHint textInputHint;

    LocalizedTextAttributeDefinitionBuilder(final String name, final LocalizedString label, final TextInputHint textInputHint) {
        super(name, label);
        this.textInputHint = textInputHint;
    }

    @Override
    protected LocalizedTextAttributeDefinitionBuilder getThis() {
        return this;
    }

    @Override
    public LocalizedTextAttributeDefinition build() {
        return new LocalizedTextAttributeDefinition(new LocalizedTextType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable(), textInputHint);
    }

    public static LocalizedTextAttributeDefinitionBuilder of(final String name, final LocalizedString label, final TextInputHint textInputHint) {
        return new LocalizedTextAttributeDefinitionBuilder(name, label, textInputHint);
    }
}
