package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public class StringType extends FieldTypeBase {

    @JsonCreator
    private StringType() {
    }

    @JsonIgnore
    public static StringType of() {
        return new StringType();
    }
}
