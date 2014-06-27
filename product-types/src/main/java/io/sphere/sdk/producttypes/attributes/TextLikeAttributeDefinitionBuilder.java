package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.LocalizedString;

abstract class TextLikeAttributeDefinitionBuilder<B, T> extends AttributeDefinitionBuilder<B, T> {
    protected TextInputHint textInputHint;

    protected TextLikeAttributeDefinitionBuilder(final String name, final LocalizedString label,
                                             final TextInputHint textInputHint) {
        super(name, label);
        this.textInputHint = textInputHint;
    }

    public B name(final TextInputHint textInputHint) {
        this.textInputHint = textInputHint;
        return getThis();
    }

    @Override
    public B searchable(final boolean isSearchable) {
        return super.searchable(isSearchable);
    }

    @Override
    public B required(final boolean isRequired) {
        return super.required(isRequired);
    }

    @Override
    public B attributeConstraint(final AttributeConstraint attributeConstraint) {
        return super.attributeConstraint(attributeConstraint);
    }
}
