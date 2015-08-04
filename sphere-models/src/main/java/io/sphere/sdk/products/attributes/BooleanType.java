package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BooleanType extends AttributeTypeBase {

    private BooleanType() {}

    @JsonCreator
    public static BooleanType of() {
        return new BooleanType();
    }
}
