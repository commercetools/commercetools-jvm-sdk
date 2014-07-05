package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.producttypes.attributes.AttributeDefinition;

import java.util.Collections;
import java.util.List;

/**
 * @see ProductTypeCreateCommand
 */
public class NewProductType extends Base {
    private final String name;
    private final String description;
    private final List<AttributeDefinition> attributes;

    protected NewProductType(final String name, final String description, final List<AttributeDefinition> attributes) {
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

    public static NewProductType of(final String name, final String description) {
        return new NewProductType(name, description, Collections.emptyList());
    }
}
