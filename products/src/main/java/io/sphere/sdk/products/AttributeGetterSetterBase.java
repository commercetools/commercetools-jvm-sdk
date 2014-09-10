package io.sphere.sdk.products;

interface AttributeGetterSetterBase<T> {
    String getName();

    AttributeMapper<T> getMapper();
}
