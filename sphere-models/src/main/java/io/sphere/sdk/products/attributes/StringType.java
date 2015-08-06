package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class StringType extends AttributeTypeBase {
    private StringType() {}

    @JsonIgnore
    public static StringType of() {
        return new StringType();
    }
}
