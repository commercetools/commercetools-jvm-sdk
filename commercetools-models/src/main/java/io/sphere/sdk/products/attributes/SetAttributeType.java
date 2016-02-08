package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

public final class SetAttributeType extends AttributeTypeBase {
    private final AttributeType elementType;

    @JsonCreator
    private SetAttributeType(final AttributeType elementType) {
        this.elementType = elementType;
    }

    public AttributeType getElementType() {
        return elementType;
    }

    @JsonIgnore
    public static SetAttributeType of(final AttributeType elementType) {
        return new SetAttributeType(elementType);
    }
}
