package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BooleanType extends AttributeTypeBase {
    @JsonIgnore
    private BooleanType() {}

    @JsonCreator
    public static BooleanType of() {
        return new BooleanType();
    }
}
