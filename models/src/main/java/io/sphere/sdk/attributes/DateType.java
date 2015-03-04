package io.sphere.sdk.attributes;

public class DateType extends AttributeTypeBase {
    private static final DateType CACHED_INSTANCE = new DateType();

    private DateType() {}

    public static DateType of() {
        return CACHED_INSTANCE;
    }
}
