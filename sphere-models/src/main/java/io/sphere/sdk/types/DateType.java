package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public class DateType extends FieldTypeBase {

    @JsonCreator
    private DateType() {
    }

    @JsonIgnore
    public static DateType of() {
        return new DateType();
    }
}
