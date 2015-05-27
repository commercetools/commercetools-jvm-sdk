package io.sphere.sdk.attributes;

public interface AttributeGetter<T> extends AttributeGetterSetterBase<T> {
    static <T> AttributeGetter<T> of(final String name, final AttributeMapper<T> mapper) {
        return AttributeGetterSetter.of(name, mapper);
    }
}
