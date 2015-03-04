package io.sphere.sdk.attributes;

public class MoneyType extends AttributeTypeBase {
    private static final MoneyType CACHED_INSTANCE = new MoneyType();

    private MoneyType() {}

    public static MoneyType of() {
        return CACHED_INSTANCE;
    }
}
