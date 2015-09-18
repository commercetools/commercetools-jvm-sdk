package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SetType extends FieldTypeBase {
    private final FieldType elementType;

    @JsonCreator
    private SetType(final FieldType elementType) {
        this.elementType = elementType;
    }

    @JsonIgnore
    public static SetType of(final FieldType elementType) {
        return new SetType(elementType);
    }

    public FieldType getElementType() {
        return elementType;
    }
}
