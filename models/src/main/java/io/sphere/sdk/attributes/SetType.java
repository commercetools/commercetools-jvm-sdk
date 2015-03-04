package io.sphere.sdk.attributes;

public class SetType extends AttributeTypeBase {
    private final AttributeType elementType;

    private SetType(final AttributeType elementType) {
        this.elementType = elementType;
    }

    public AttributeType getElementType() {
        return elementType;
    }

    public static SetType of(final AttributeType elementType) {
        return new SetType(elementType);
    }
}
