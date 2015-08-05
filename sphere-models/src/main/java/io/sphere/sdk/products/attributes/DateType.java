package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DateType extends AttributeTypeBase {
    private DateType() {}

    @JsonIgnore
    public static DateType of() {
        return new DateType();
    }
}
