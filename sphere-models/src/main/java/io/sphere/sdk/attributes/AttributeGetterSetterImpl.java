package io.sphere.sdk.attributes;

import io.sphere.sdk.models.Base;

class AttributeGetterSetterImpl<T> extends Base implements AttributeGetterSetter<T> {

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

    @Override
    public Attribute valueOf(T input) {
        return Attribute.of(this, input);
    }
}
