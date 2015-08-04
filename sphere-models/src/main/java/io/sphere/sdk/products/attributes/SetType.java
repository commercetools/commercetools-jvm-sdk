package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SetType extends AttributeTypeBase {
    private final AttributeType elementType;

    @JsonCreator
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
