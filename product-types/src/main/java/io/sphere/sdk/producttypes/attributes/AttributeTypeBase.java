package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.Base;

public abstract class AttributeTypeBase extends Base implements AttributeType {
    private final String name;

    protected AttributeTypeBase(final String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }
}
