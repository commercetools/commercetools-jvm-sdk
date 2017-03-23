package io.sphere.sdk.products;

import io.sphere.sdk.products.attributes.Attribute;

import java.util.List;

class AttributeContainerImpl extends AttributeContainerBase {
    private final List<Attribute> attributes;

    AttributeContainerImpl(final List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<Attribute> getAttributes() {
        return attributes;
    }


    public static AttributeContainer of(final List<Attribute> attributes) {
        return new AttributeContainerImpl(attributes);
    }
}
