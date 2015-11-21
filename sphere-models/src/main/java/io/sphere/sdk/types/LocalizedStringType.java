package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public class LocalizedStringType extends FieldTypeBase {

    @JsonCreator
    private LocalizedStringType() {
    }

    @JsonIgnore
    public static LocalizedStringType of() {
        return new LocalizedStringType();
    }
}
