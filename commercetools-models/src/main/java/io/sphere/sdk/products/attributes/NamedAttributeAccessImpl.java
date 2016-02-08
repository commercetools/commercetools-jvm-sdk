package io.sphere.sdk.products.attributes;

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

    public AttributeMapper<T> attributeMapper() {
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
    public boolean canHandle(final AttributeDefinition attributeDefinition) {
        return attributeAccess.canHandle(attributeDefinition);
    }

    @Override
    public NamedAttributeAccess<T> ofName(final String name) {
        return attributeAccess.ofName(name);
    }
}
