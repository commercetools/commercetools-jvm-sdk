package io.sphere.sdk.products;

public interface AttributeGetter<M, T> extends AttributeGetterSetterBase<T> {
    public static <M, T> AttributeGetter<M, T> of(final String name, final AttributeMapper<T> mapper) {
        return AttributeGetterSetter.<M, T>of(name, mapper);
    }
}
