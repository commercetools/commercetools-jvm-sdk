package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DateType extends AttributeTypeBase {
    private DateType() {}

    @JsonIgnore
    public static DateType of() {
        return new DateType();
    }
}
