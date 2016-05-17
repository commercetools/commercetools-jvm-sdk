package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.products.attributes.AttributeDefinition;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @see io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand
 */
@JsonDeserialize(as = ProductTypeDraftImpl.class)
public interface ProductTypeDraft extends WithKey {
    String getName();

    String getDescription();

    List<AttributeDefinition> getAttributes();

    @Nullable
    String getKey();

    /**
     * Creates a input object to create a {@link ProductType}.
     *
     * @param key unique key to identify the created {@link ProductType}
     * @param name the name of the product type, SPHERE.IO does not check that the name will be unique so it is best practice to check if a product type of this name already exists
     * @param description description of the product type
     * @param attributes definitions of attributes for the product type
     * @return draft for a product type
     */
    static ProductTypeDraft of(@Nullable final String key, final String name, final String description, final List<AttributeDefinition> attributes) {
        return new ProductTypeDraftImpl(key, name, description, attributes);
    }
}
