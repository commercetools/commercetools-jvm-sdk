package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;


public class TextAttributeDefinitionBuilder extends BaseBuilder<TextAttributeDefinition> {
    private final TextInputHint textInputHint;

    TextAttributeDefinitionBuilder(final String name, final LocalizedString label, final TextInputHint textInputHint) {
        super(name, label);
        this.textInputHint = textInputHint;
    }

    @Override
    protected TextAttributeDefinitionBuilder getThis() {
        return this;
    }

    @Override
    public TextAttributeDefinition build() {
        return new TextAttributeDefinition(new TextType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable(), textInputHint);
    }

    public static TextAttributeDefinitionBuilder of(final String name, final LocalizedString label, final TextInputHint textInputHint) {
        return new TextAttributeDefinitionBuilder(name, label, textInputHint);
    }
}
