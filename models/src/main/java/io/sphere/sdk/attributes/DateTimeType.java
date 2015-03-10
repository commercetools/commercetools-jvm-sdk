package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DateTimeType extends AttributeTypeBase {
    @JsonIgnore
    private DateTimeType() {}

    @JsonCreator
    public static DateTimeType of() {
        return new DateTimeType();
    }
}
