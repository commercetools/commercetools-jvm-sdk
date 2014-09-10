package io.sphere.sdk.products;

import io.sphere.sdk.models.Base;

class AttributeGetterSetterImpl<M, T> extends Base implements AttributeGetterSetter<M, T> {

    private final String name;
    private final AttributeMapper<T> mapper;

    AttributeGetterSetterImpl(final String name, final AttributeMapper<T> mapper) {

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
