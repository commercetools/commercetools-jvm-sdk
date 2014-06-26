package io.sphere.sdk.producttypes;

import java.util.List;

public final class NewProductType {
    private final String name;
    private final String description;
    private final List<AttributeDefinition> attributes;

    private NewProductType(final String name, final String description, final List<AttributeDefinition> attributes) {
        this.name = name;
        this.description = description;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<AttributeDefinition> getAttributes() {
        return attributes;
    }

    public static NewProductType of(final String name, final String description, final List<AttributeDefinition> attributes) {
        return new NewProductType(name, description, attributes);
    }
}
