package io.sphere.sdk.attributes;

interface AttributeGetterSetterBase<T> {
    String getName();

    AttributeMapper<T> getMapper();
}
