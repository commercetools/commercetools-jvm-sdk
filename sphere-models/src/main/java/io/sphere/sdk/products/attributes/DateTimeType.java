package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DateTimeType extends AttributeTypeBase {
    private DateTimeType() {}

    @JsonIgnore
    public static DateTimeType of() {
        return new DateTimeType();
    }
}
