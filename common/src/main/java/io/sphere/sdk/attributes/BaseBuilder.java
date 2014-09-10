package io.sphere.sdk.attributes;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;

/**
 *
 * @param <T> the target AttributeDefinition type
 */
abstract class BaseBuilder<T extends AttributeDefinition, B extends AttributeDefinitionBuilder<B>> extends AttributeDefinitionBuilder<B> implements Builder<T> {

    BaseBuilder(final String name, final LocalizedString label) {
        super(name, label);
    }

    public B required(final boolean isRequired) {
        this.isRequired = isRequired;
        return getThis();
    }

    public B isRequired(final boolean isRequired) {
        return isRequired(isRequired);
    }

    public B searchable(final boolean isSearchable) {
        this.isSearchable = isSearchable;
        return getThis();
    }

    public B isSearchable(final boolean isSearchable) {
        return searchable(isSearchable);
    }

    protected abstract B getThis();
}
