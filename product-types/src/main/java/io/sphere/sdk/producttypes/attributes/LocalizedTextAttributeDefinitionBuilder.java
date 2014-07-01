package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

class LocalizedTextAttributeDefinitionBuilder extends BaseBuilder<LocalizedTextAttributeDefinition> {

    private final TextInputHint textInputHint;

    public LocalizedTextAttributeDefinitionBuilder(final String name, final LocalizedString label, final TextInputHint textInputHint) {
        super(name, label);
        this.textInputHint = textInputHint;
    }

    @Override
    protected BaseBuilder<LocalizedTextAttributeDefinition> getThis() {
        return this;
    }

    @Override
    public LocalizedTextAttributeDefinition build() {
        return new LocalizedTextAttributeDefinition(new LocalizedTextType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable(), textInputHint);
    }
}
