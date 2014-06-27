package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

public final class LocalizedTextAttributeDefinitionBuilder extends TextLikeAttributeDefinitionBuilder<LocalizedTextAttributeDefinitionBuilder, LocalizedTextAttributeDefinition> {

    private LocalizedTextAttributeDefinitionBuilder(final String name, final LocalizedString label, final TextInputHint textInputHint) {
        super(name, label, textInputHint);
    }

    public static LocalizedTextAttributeDefinitionBuilder of(final String name, final LocalizedString label, final TextInputHint textInputHint) {
        return new LocalizedTextAttributeDefinitionBuilder(name, label, textInputHint);
    }

    @Override
    public LocalizedTextAttributeDefinition build() {
        return new LocalizedTextAttributeDefinition(getAttributeType(), getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable(), textInputHint);
    }

    @Override
    protected LocalizedTextAttributeDefinitionBuilder getThis() {
        return this;
    }


    @Override
    protected AttributeType getAttributeType() {
        return new LocalizedTextType();
    }
}
