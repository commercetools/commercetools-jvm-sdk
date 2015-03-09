package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class NumberType extends AttributeTypeBase {
    private static final NumberType CACHED_INSTANCE = new NumberType();

    @JsonIgnore
    private NumberType() {}

    @JsonCreator
    public static NumberType of() {
        return CACHED_INSTANCE;
    }
}
