package io.sphere.sdk.attributes;

public class BooleanType extends AttributeTypeBase {
    private static final BooleanType CACHED_INSTANCE = new BooleanType();

    private BooleanType() {}

    public static BooleanType of() {
        return CACHED_INSTANCE;
    }
}
