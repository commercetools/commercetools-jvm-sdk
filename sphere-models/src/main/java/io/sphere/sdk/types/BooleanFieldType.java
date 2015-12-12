package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public class BooleanFieldType extends FieldTypeBase {

    @JsonCreator
    private BooleanFieldType() {
    }

    @JsonIgnore
    public static BooleanFieldType of() {
        return new BooleanFieldType();
    }
}
