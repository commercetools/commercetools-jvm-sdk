package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BooleanType extends FieldTypeBase {

    @JsonCreator
    private BooleanType() {
    }

    @JsonIgnore
    public static BooleanType of() {
        return new BooleanType();
    }
}
