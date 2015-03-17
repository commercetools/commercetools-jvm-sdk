package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class NumberType extends AttributeTypeBase {
    private NumberType() {}

    @JsonIgnore
    public static NumberType of() {
        return new NumberType();
    }
}
