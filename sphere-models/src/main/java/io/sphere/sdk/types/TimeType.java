package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public class TimeType extends FieldTypeBase {

    @JsonCreator
    private TimeType() {
    }

    @JsonIgnore
    public static TimeType of() {
        return new TimeType();
    }
}
