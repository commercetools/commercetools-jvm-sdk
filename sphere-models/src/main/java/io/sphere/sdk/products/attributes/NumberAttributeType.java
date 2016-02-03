package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class NumberAttributeType extends AttributeTypeBase {
    private NumberAttributeType() {}

    @JsonIgnore
    public static NumberAttributeType of() {
        return new NumberAttributeType();
    }
}
