package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DateTimeAttributeType extends AttributeTypeBase {
    private DateTimeAttributeType() {}

    @JsonIgnore
    public static DateTimeAttributeType of() {
        return new DateTimeAttributeType();
    }
}
