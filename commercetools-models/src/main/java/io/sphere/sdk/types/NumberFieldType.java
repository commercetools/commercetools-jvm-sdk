package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public final class NumberFieldType extends FieldTypeBase {

    @JsonCreator
    private NumberFieldType() {
    }

    @JsonIgnore
    public static NumberFieldType of() {
        return new NumberFieldType();
    }
}
