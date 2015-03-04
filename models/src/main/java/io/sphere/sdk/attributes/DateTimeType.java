package io.sphere.sdk.attributes;

public class DateTimeType extends AttributeTypeBase {
    private static final DateTimeType CACHED_INSTANCE = new DateTimeType();

    private DateTimeType() {}

    public static DateTimeType of() {
        return CACHED_INSTANCE;
    }
}
