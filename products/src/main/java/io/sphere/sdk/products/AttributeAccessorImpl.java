package io.sphere.sdk.products;

import io.sphere.sdk.models.Base;

class AttributeAccessorImpl<M, T> extends Base implements AttributeAccessor<M, T> {

    private final String name;
    private final AttributeMapper<T> mapper;

    AttributeAccessorImpl(final String name, final AttributeMapper<T> mapper) {

        this.name = name;
        this.mapper = mapper;
    }

    public String getName() {
        return name;
    }

    public AttributeMapper<T> getMapper() {
        return mapper;
    }
}
