package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SetType extends AttributeTypeBase {
    private final AttributeType elementType;

    private SetType(final AttributeType elementType) {
        this.elementType = elementType;
    }

    public AttributeType getElementType() {
        return elementType;
    }

    @JsonIgnore
    public static SetType of(final AttributeType elementType) {
        return new SetType(elementType);
    }
}
