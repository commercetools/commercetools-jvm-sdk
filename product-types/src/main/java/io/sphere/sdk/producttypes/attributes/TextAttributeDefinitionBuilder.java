package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

public class TextAttributeDefinitionBuilder extends AttributeDefinitionBuilder<TextAttributeDefinitionBuilder, TextAttributeDefinition> {

    private TextInputHint textInputHint;

    protected TextAttributeDefinitionBuilder(final String name, final LocalizedString label,
                                             final TextInputHint textInputHint) {
        super(name, label);
        this.textInputHint = textInputHint;
    }

    public static TextAttributeDefinitionBuilder of(final String name, final LocalizedString label,
                                                    final TextInputHint textInputHint) {
        return new TextAttributeDefinitionBuilder(name, label, textInputHint);
    }

    @Override
    protected TextAttributeDefinitionBuilder getThis() {
        return this;
    }

    @Override
    public TextAttributeDefinition build() {
        return new TextAttributeDefinition(getAttributeType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable(), textInputHint);
    }


    public TextAttributeDefinitionBuilder name(final TextInputHint textInputHint) {
        this.textInputHint = textInputHint;
        return getThis();
    }

    @Override
    protected AttributeType getAttributeType() {
        return new TextType();
    }

    @Override
    public TextAttributeDefinitionBuilder searchable(final boolean isSearchable) {
        return super.searchable(isSearchable);
    }

    @Override
    public TextAttributeDefinitionBuilder required(final boolean isRequired) {
        return super.required(isRequired);
    }

    @Override
    public TextAttributeDefinitionBuilder attributeConstraint(final AttributeConstraint attributeConstraint) {
        return super.attributeConstraint(attributeConstraint);
    }
}
