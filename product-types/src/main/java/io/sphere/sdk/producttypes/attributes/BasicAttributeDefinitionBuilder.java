package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;

/**
 *
 * @param <T> the target AttributeDefinition type
 */
public abstract class BasicAttributeDefinitionBuilder<T extends AttributeDefinition> extends AttributeDefinitionBuilder<BasicAttributeDefinitionBuilder<T>> implements Builder<T> {

    BasicAttributeDefinitionBuilder(final String name, final LocalizedString label) {
        super(name, label);
    }

    public BasicAttributeDefinitionBuilder<T> required(final boolean isRequired) {
        this.isRequired = isRequired;
        return getThis();
    }


    public BasicAttributeDefinitionBuilder<T> searchable(final boolean isSearchable) {
        this.isSearchable = isSearchable;
        return getThis();
    }
}
