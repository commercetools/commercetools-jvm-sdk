package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class StringAttributeType extends AttributeTypeBase {
    private StringAttributeType() {}

    @JsonIgnore
    public static StringAttributeType of() {
        return new StringAttributeType();
    }
}
