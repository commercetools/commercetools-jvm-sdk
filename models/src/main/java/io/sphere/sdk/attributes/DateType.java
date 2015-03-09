package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DateType extends AttributeTypeBase {
    private static final DateType CACHED_INSTANCE = new DateType();

    @JsonIgnore
    private DateType() {}

    @JsonCreator
    public static DateType of() {
        return CACHED_INSTANCE;
    }
}
