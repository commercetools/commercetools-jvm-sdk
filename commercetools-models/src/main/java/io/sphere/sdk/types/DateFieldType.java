package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public final class DateFieldType extends FieldTypeBase {

    @JsonCreator
    private DateFieldType() {
    }

    @JsonIgnore
    public static DateFieldType of() {
        return new DateFieldType();
    }
}
