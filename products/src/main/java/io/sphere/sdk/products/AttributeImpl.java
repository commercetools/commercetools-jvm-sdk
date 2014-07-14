package io.sphere.sdk.products;

import io.sphere.sdk.models.Base;

class AttributeImpl<T> extends Base implements TypedAttribute<T> {
    private final String name;
    private final T value;

    public AttributeImpl(final String name, final T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }
}
