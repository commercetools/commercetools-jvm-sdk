package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DateTimeType extends AttributeTypeBase {
    private static final DateTimeType CACHED_INSTANCE = new DateTimeType();

    @JsonIgnore
    private DateTimeType() {}

    @JsonCreator
    public static DateTimeType of() {
        return CACHED_INSTANCE;
    }
}
