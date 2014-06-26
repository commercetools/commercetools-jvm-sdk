package io.sphere.sdk.producttypes.attributes;

public abstract class AttributeTypeBase implements AttributeType {
    private final String name;

    protected AttributeTypeBase(final String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }
}
