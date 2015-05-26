package io.sphere.sdk.attributes;


public interface AttributeSetter<T> extends AttributeGetterSetterBase<T> {
    static <T> AttributeSetter<T> of(final String name, final AttributeMapper<T> mapper) {
        return AttributeGetterSetter.of(name, mapper);
    }

    Attribute valueOf(final T input);
}
