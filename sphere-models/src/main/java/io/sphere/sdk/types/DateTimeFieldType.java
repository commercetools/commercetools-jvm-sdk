package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public final class DateTimeFieldType extends FieldTypeBase {

    @JsonCreator
    private DateTimeFieldType() {
    }

    @JsonIgnore
    public static DateTimeFieldType of() {
        return new DateTimeFieldType();
    }
}
