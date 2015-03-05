package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TimeType extends AttributeTypeBase {
    private static final TimeType CACHED_INSTANCE = new TimeType();

    @JsonIgnore
    private TimeType() {}

    @JsonCreator
    public static TimeType of() {
        return CACHED_INSTANCE;
    }
}
