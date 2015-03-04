package io.sphere.sdk.attributes;

public class TimeType extends AttributeTypeBase {
    private static final TimeType CACHED_INSTANCE = new TimeType();

    private TimeType() {}

    public static TimeType of() {
        return CACHED_INSTANCE;
    }
}
