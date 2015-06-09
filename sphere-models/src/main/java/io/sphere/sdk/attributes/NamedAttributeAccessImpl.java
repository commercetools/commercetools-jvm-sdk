package io.sphere.sdk.attributes;

import io.sphere.sdk.models.Base;

class NamedAttributeAccessImpl<T> extends Base implements NamedAttributeAccess<T> {

    private final String name;
    private final AttributeMapper<T> mapper;

    NamedAttributeAccessImpl(final String name, final AttributeMapper<T> mapper) {

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

    @Override
    public AttributeDraft draftOf(T input) {
        return AttributeDraft.of(this, input);
    }
}
