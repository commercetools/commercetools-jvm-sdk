package io.sphere.sdk.attributes;


public interface AttributeSetter<M, T> extends AttributeGetterSetterBase<T> {
    public static <M, T> AttributeSetter<M, T> of(final String name, final AttributeMapper<T> mapper) {
        return AttributeGetterSetter.<M, T>of(name, mapper);
    }
}
