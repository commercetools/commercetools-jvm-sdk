package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public final class TimeFieldType extends FieldTypeBase {

    @JsonCreator
    private TimeFieldType() {
    }

    @JsonIgnore
    public static TimeFieldType of() {
        return new TimeFieldType();
    }
}
