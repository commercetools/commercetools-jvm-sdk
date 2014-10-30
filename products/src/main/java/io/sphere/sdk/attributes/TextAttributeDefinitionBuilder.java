package io.sphere.sdk.attributes;

import io.sphere.sdk.models.LocalizedStrings;


public class TextAttributeDefinitionBuilder extends BaseBuilder<TextAttributeDefinition, TextAttributeDefinitionBuilder> {
    private final TextInputHint textInputHint;

    TextAttributeDefinitionBuilder(final String name, final LocalizedStrings label, final TextInputHint textInputHint) {
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

    public static TextAttributeDefinitionBuilder of(final String name, final LocalizedStrings label, final TextInputHint textInputHint) {
        return new TextAttributeDefinitionBuilder(name, label, textInputHint);
    }
}
