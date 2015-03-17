package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DateTimeType extends AttributeTypeBase {
    private DateTimeType() {}

    @JsonIgnore
    public static DateTimeType of() {
        return new DateTimeType();
    }
}
