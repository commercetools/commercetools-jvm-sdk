package io.sphere.sdk.attributes;

import io.sphere.sdk.models.Base;

public abstract class AttributeTypeBase extends Base implements AttributeType {
    private final String name;

    AttributeTypeBase(final String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }
}
