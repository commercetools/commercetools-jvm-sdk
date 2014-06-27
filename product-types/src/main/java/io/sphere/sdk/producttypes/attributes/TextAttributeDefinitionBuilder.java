package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

public final class TextAttributeDefinitionBuilder extends TextLikeAttributeDefinitionBuilder<TextAttributeDefinitionBuilder, TextAttributeDefinition> {

    private TextAttributeDefinitionBuilder(final String name, final LocalizedString label, final TextInputHint textInputHint) {
        super(name, label, textInputHint);
    }

    public static TextAttributeDefinitionBuilder of(final String name, final LocalizedString label, final TextInputHint textInputHint) {
        return new TextAttributeDefinitionBuilder(name, label, textInputHint);
    }

    @Override
    public TextAttributeDefinition build() {
        return new TextAttributeDefinition(getAttributeType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable(), textInputHint);
    }

    @Override
    protected TextAttributeDefinitionBuilder getThis() {
        return this;
    }


    @Override
    protected AttributeType getAttributeType() {
        return new TextType();
    }
}
