package io.sphere.sdk.products;

public interface TypedAttribute<T> extends Attribute {
    T getValue();
}
