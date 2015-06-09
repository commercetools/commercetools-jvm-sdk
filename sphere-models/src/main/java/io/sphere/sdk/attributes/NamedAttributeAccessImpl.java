package io.sphere.sdk.attributes;

import io.sphere.sdk.models.Base;

final class NamedAttributeAccessImpl<T> extends Base implements NamedAttributeAccess<T> {

    private final String name;
    private final AttributeAccess<T> attributeAccess;

    NamedAttributeAccessImpl(final String name, final AttributeAccess<T> attributeAccess) {
        this.name = name;
        this.attributeAccess = attributeAccess;
    }

    public String getName() {
        return name;
    }

    public AttributeMapper<T> getMapper() {
        return attributeAccess.attributeMapper();
    }

    @Override
    public Attribute valueOf(T input) {
        return Attribute.of(this, input);
    }

    @Override
    public AttributeDraft draftOf(T input) {
        return AttributeDraft.of(this, input);
    }

    @Override
    public AttributeAccess<T> getAttributeAccess() {
        return attributeAccess;
    }
}
