package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class NumberType extends AttributeTypeBase {
    @JsonIgnore
    private NumberType() {}

    @JsonCreator
    public static NumberType of() {
        return new NumberType();
    }
}
