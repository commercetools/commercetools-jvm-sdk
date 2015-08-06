package io.sphere.sdk.producttypes;

import io.sphere.sdk.products.attributes.AttributeDefinition;

import java.util.List;

class MetaProductTypeImpl implements MetaProductType {
    private final List<AttributeDefinition> definitions;

    MetaProductTypeImpl(final List<AttributeDefinition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public List<AttributeDefinition> getAttributes() {
        return definitions;
    }
}
