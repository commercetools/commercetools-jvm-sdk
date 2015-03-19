package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TimeType extends AttributeTypeBase {
    private TimeType() {}

    @JsonIgnore
    public static TimeType of() {
        return new TimeType();
    }
}
