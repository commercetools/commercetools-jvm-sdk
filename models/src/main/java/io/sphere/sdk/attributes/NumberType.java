package io.sphere.sdk.attributes;

public class NumberType extends AttributeTypeBase {
    private static final NumberType CACHED_INSTANCE = new NumberType();

    private NumberType() {}

    public static NumberType of() {
        return CACHED_INSTANCE;
    }
}
