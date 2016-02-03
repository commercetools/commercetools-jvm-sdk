package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @see Custom
 */
public final class SetFieldType extends FieldTypeBase {
    private final FieldType elementType;

    @JsonCreator
    private SetFieldType(final FieldType elementType) {
        this.elementType = elementType;
    }

    @JsonIgnore
    public static SetFieldType of(final FieldType elementType) {
        return new SetFieldType(elementType);
    }

    public FieldType getElementType() {
        return elementType;
    }
}
