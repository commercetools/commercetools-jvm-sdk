package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public class DateTimeType extends FieldTypeBase {

    @JsonCreator
    private DateTimeType() {
    }

    @JsonIgnore
    public static DateTimeType of() {
        return new DateTimeType();
    }
}
