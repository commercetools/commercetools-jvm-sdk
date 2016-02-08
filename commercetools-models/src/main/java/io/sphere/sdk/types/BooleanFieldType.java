package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Type for custom fields which only allows {@code true} or {@code false} as values.
 * @see Custom
 */
public final class BooleanFieldType extends FieldTypeBase {

    @JsonCreator
    private BooleanFieldType() {
    }

    @JsonIgnore
    public static BooleanFieldType of() {
        return new BooleanFieldType();
    }
}
