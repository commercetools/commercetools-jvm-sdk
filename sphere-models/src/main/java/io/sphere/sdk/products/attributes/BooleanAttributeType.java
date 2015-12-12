package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BooleanAttributeType extends AttributeTypeBase {

    private BooleanAttributeType() {}

    @JsonCreator
    public static BooleanAttributeType of() {
        return new BooleanAttributeType();
    }
}
