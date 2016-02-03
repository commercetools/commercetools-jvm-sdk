package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public final class LocalizedStringFieldType extends FieldTypeBase {

    @JsonCreator
    private LocalizedStringFieldType() {
    }

    @JsonIgnore
    public static LocalizedStringFieldType of() {
        return new LocalizedStringFieldType();
    }
}
