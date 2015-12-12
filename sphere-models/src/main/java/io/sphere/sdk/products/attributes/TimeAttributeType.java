package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TimeAttributeType extends AttributeTypeBase {
    private TimeAttributeType() {}

    @JsonIgnore
    public static TimeAttributeType of() {
        return new TimeAttributeType();
    }
}
