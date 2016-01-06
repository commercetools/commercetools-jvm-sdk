package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.products.attributes.AttributeDefinition;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @see io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand
 */
public class ProductTypeDraft extends Base {
    @Nullable
    private final String key;
    private final String name;
    private final String description;
    private final List<AttributeDefinition> attributes;

    @JsonCreator
    ProductTypeDraft(@Nullable final String key, final String name, final String description, final List<AttributeDefinition> attributes) {
        this.key = key;
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

    @Nullable
    public String getKey() {
        return key;
    }

    /**
     * Creates a input object to create a {@link ProductType}.
     *
     * @param name the name of the product type, SPHERE.IO does not check that the name will be unique so it is best practice to check if a product type of this name already exists
     * @param description description of the product type
     * @param attributes definitions of attributes for the product type
     * @return draft for a product type
     * @deprecated use {@link #of(String, String, String, List)} since new product types should include a key
     */
    @Deprecated
    public static ProductTypeDraft of(final String name, final String description, final List<AttributeDefinition> attributes) {
        return of(null, name, description, attributes);
    }

    /**
     * Creates a input object to create a {@link ProductType}.
     *
     * @param key unique key to identify the created {@link ProductType}
     * @param name the name of the product type, SPHERE.IO does not check that the name will be unique so it is best practice to check if a product type of this name already exists
     * @param description description of the product type
     * @param attributes definitions of attributes for the product type
     * @return draft for a product type
     */
    public static ProductTypeDraft of(@Nullable final String key, final String name, final String description, final List<AttributeDefinition> attributes) {
        return new ProductTypeDraft(key, name, description, attributes);
    }
}
