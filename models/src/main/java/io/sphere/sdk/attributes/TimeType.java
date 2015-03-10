package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class TimeType extends AttributeTypeBase {
    @JsonIgnore
    private TimeType() {}

    @JsonCreator
    public static TimeType of() {
        return new TimeType();
    }
}
