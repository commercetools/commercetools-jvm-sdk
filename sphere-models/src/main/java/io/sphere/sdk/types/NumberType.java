package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public class NumberType extends FieldTypeBase {

    @JsonCreator
    private NumberType() {
    }

    @JsonIgnore
    public static NumberType of() {
        return new NumberType();
    }
}
