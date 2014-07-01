package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

class TextAttributeDefinitionBuilder extends BaseBuilder<TextAttributeDefinition> {
    private final TextInputHint textInputHint;

    public TextAttributeDefinitionBuilder(final String name, final LocalizedString label, final TextInputHint textInputHint) {
        super(name, label);
        this.textInputHint = textInputHint;
    }

    @Override
    protected BaseBuilder<TextAttributeDefinition> getThis() {
        return this;
    }

    @Override
    public TextAttributeDefinition build() {
        return new TextAttributeDefinition(new TextType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable(), textInputHint);
    }
}
