package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BooleanType extends AttributeTypeBase {
    private static final BooleanType CACHED_INSTANCE = new BooleanType();

    @JsonIgnore
    private BooleanType() {}

    @JsonCreator
    public static BooleanType of() {
        return CACHED_INSTANCE;
    }
}
