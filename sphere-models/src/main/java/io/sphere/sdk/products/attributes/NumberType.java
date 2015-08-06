package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NumberType extends AttributeTypeBase {
    private NumberType() {}

    @JsonIgnore
    public static NumberType of() {
        return new NumberType();
    }
}
