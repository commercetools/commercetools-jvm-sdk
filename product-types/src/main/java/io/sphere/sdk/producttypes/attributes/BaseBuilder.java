package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;

/**
 *
 * @param <T> the target AttributeDefinition type
 */
public abstract class BaseBuilder<T extends AttributeDefinition> extends AttributeDefinitionBuilder<BaseBuilder<T>> implements Builder<T> {

    BaseBuilder(final String name, final LocalizedString label) {
        super(name, label);
    }

    public BaseBuilder<T> required(final boolean isRequired) {
        this.isRequired = isRequired;
        return getThis();
    }

    public BaseBuilder<T> isRequired(final boolean isRequired) {
        return isRequired(isRequired);
    }

    public BaseBuilder<T> searchable(final boolean isSearchable) {
        this.isSearchable = isSearchable;
        return getThis();
    }

    public BaseBuilder<T> isSearchable(final boolean isSearchable) {
        return searchable(isSearchable);
    }
}
