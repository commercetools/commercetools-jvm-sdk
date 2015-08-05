package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.products.attributes.AttributeDefinition;

import java.util.List;

/**
 * @see io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand
 */
public class ProductTypeDraft extends Base {
    private final String name;
    private final String description;
    private final List<AttributeDefinition> attributes;

    ProductTypeDraft(final String name, final String description, final List<AttributeDefinition> attributes) {
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

    /**
     * Creates a input object to create a {@link ProductType}.
     *
     * @param name the name of the product type, SPHERE.IO does not check that the name will be unique so it is best practice to check if a product type of this name already exists
     * @param description description of the product type
     * @param attributes definitions of attributes for the product type
     * @return draft for a product type
     */
    public static ProductTypeDraft of(final String name, final String description, final List<AttributeDefinition> attributes) {
        return new ProductTypeDraft(name, description, attributes);
    }
}
